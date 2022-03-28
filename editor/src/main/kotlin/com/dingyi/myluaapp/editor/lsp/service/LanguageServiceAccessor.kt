package com.dingyi.myluaapp.editor.lsp.service

import com.dingyi.myluaapp.common.ktx.checkNotNull
import com.dingyi.myluaapp.editor.lsp.server.LanguageServerWrapper
import com.dingyi.myluaapp.editor.lsp.connect.StreamConnectionProvider
import com.dingyi.myluaapp.editor.lsp.server.definition.LanguageServerDefinition
import com.dingyi.myluaapp.plugin.api.editor.Editor
import com.dingyi.myluaapp.plugin.api.project.Project
import org.eclipse.lsp4j.ServerCapabilities
import org.eclipse.lsp4j.services.LanguageServer
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import java.util.function.Predicate


object LanguageServiceAccessor {

    private val startedServers: MutableSet<LanguageServerWrapper> = HashSet()
    private val providersToLSDefinitions =
        mutableMapOf<StreamConnectionProvider, LanguageServerDefinition>()

    /**
     * This is meant for test code to clear state that might have leaked from other
     * tests. It isn't meant to be used in production code.
     */
    fun clearStartedServers() {
        synchronized(startedServers) {
            startedServers.forEach(Consumer { obj: LanguageServerWrapper -> obj.stop() })
            startedServers.clear()
        }
    }

    /**
     * Get the requested language server instance for the given file. Starts the language server if not already started.
     * @param editor currentEditor
     * @param lsDefinition
     * @param capabilitiesPredicate a predicate to check capabilities
     * @return a LanguageServer for the given file, which is defined with provided server ID and conforms to specified request.
     *  If {@code capabilitesPredicate} does not test positive for the server's capabilities, {@code null} is returned.
     */
    fun getInitializedLanguageServer(
        editor: Editor,
        lsDefinition: LanguageServerDefinition,
        capabilitiesPredicate: Predicate<ServerCapabilities?>?
    ): CompletableFuture<LanguageServer> {
        val wrapper =
            getLSWrapper(editor.getProject(), lsDefinition)
        if (capabilitiesComply(wrapper, capabilitiesPredicate)) {
            wrapper.connect(editor)
            return wrapper.getInitializedServer()
        }
        return CompletableFuture.completedFuture(null)
    }


    fun getLSWrapper(
        project: Project,
        serverDefinition: LanguageServerDefinition
    ): LanguageServerWrapper {
        var wrapper: LanguageServerWrapper? = null
        synchronized(startedServers) {
            for (startedWrapper in getStartedLSWrappers(project)) {
                if (startedWrapper.serverDefinition == serverDefinition) {
                    wrapper = startedWrapper
                    break
                }
            }
            if (wrapper == null) {
                wrapper = LanguageServerWrapper(
                    project,
                    serverDefinition
                )
                wrapper?.start()
            }
            wrapper?.let { startedServers.add(it) }

        }
        return wrapper.checkNotNull()
    }


    private fun getStartedLSWrappers(
        project: Project
    ): List<LanguageServerWrapper> {
        return getStartedLSWrappers { wrapper ->
            wrapper.canOperate(project)
        }
    }


    private fun getStartedLSWrappers(
        editor: Editor
    ): List<LanguageServerWrapper> {
        return getStartedLSWrappers { wrapper ->
            wrapper.canOperate(editor)
        }
    }


    private fun getStartedLSWrappers(predicate: (LanguageServerWrapper) -> Boolean): List<LanguageServerWrapper> {
        return startedServers.filter(predicate)
    }


    /**
     * Checks if the given `wrapper`'s capabilities comply with the given
     * `capabilitiesPredicate`.
     *
     * @param wrapper
     * the server that's capabilities are tested with
     * `capabilitiesPredicate`
     * @param capabilitiesPredicate
     * predicate testing the capabilities of `wrapper`.
     * @return The result of applying the capabilities of `wrapper` to
     * `capabilitiesPredicate`, or `false` if
     * `capabilitiesPredicate == null` or
     * `wrapper.getServerCapabilities() == null`
     */
    private fun capabilitiesComply(
        wrapper: LanguageServerWrapper,
        capabilitiesPredicate: Predicate<ServerCapabilities?>?
    ): Boolean {
        return capabilitiesPredicate == null || wrapper.getServerCapabilities() == null /* null check is workaround for https://github.com/TypeFox/ls-api/issues/47 */ || capabilitiesPredicate.test(
            wrapper.getServerCapabilities()
        )
    }


}