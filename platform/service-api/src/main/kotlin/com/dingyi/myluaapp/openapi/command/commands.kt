package com.dingyi.myluaapp.openapi.command


import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import com.dingyi.myluaapp.openapi.util.Disposable
import com.dingyi.myluaapp.openapi.util.Disposer
import com.dingyi.myluaapp.openapi.util.Emitter
import java.util.LinkedList

data class ICommandEvent(
    val commandId: String,
    val args: CommandContext
)

interface ICommandService {
    val onWillExecuteCommand: Emitter<Unit, ICommandEvent>
    val onDidExecuteCommand: Emitter<Unit, ICommandEvent>
    fun executeCommand(commandId: String, context: CommandContext): CommandContext
}

fun interface ICommandHandler {
    operator fun invoke(serviceRegistry: ServiceRegistry, context: CommandContext)

    companion object {
        fun create(f: (context: CommandContext) -> Unit): ICommandHandler {
            return ICommandHandler { _, context -> f(context) }
        }

        fun create(f: () -> Unit): ICommandHandler {
            return ICommandHandler { _, _ -> f() }
        }
    }
}

data class ICommand(
    val id: String,
    val handler: ICommandHandler
)


typealias CommandListener = (commandId: String) -> Unit

class CommandContext {
    private val args = mutableListOf<Any?>()

    var result: Any? = null

    fun addArg(arg: Any?) {
        args.add(arg)
    }

    fun addArgs(vararg args: Any?) {
        this.args.addAll(args)
    }

    fun <T : Any> getArg(index: Int): T {
        return args[index] as T
    }

    fun <T : Any> getArg(clazz: Class<T>): T {
        return args.first { clazz.isInstance(it) } as T
    }

    fun <T : Any> getArgOrNull(clazz: Class<T>): T? {
        return args.firstOrNull { clazz.isInstance(it) } as T?
    }

    fun <T : Any> getArgOrNull(index: Int): T? {
        return args.getOrNull(index) as T?
    }

    fun <T> resultAs(): T {
        return result as T
    }


}


inline fun <reified T : Any> CommandContext.getArg(): T {
    return this.getArg(T::class.java)
}

interface ICommandRegistry {
    val onDidRegisterCommand: Emitter<Unit, CommandListener>
    fun registerCommand(idOrCommand: String, handler: ICommandHandler): Disposable
    fun registerCommand(command: ICommand): Disposable
    fun registerCommandAlias(oldId: String, newId: String): Disposable
    fun getCommand(id: String): ICommand?
    fun getCommands(): Map<String, ICommand>
}


object DefaultCommandRegistry : ICommandRegistry {

    private val commands = mutableMapOf<String, LinkedList<ICommand>>()

    override val onDidRegisterCommand = Emitter<Unit, CommandListener>()

    override fun registerCommand(idOrCommand: String, handler: ICommandHandler): Disposable {
        if (idOrCommand.isEmpty()) {
            throw IllegalArgumentException("invalid command")
        }
        return registerCommand(
            ICommand(
                id = idOrCommand,
                handler = handler
            )
        )
    }

    override fun registerCommand(command: ICommand): Disposable {
        if (command.id.isEmpty()) {
            throw IllegalArgumentException("invalid command")
        }
        val commands = commands.getOrPut(command.id) { LinkedList() }
        val ret = Disposer.newDisposable()
        Disposer.register(ret) {
            commands.remove(command)
            if (commands.isEmpty()) {
                this.commands.remove(command.id)
            }
        }
        onDidRegisterCommand.emit {
            it(command.id)
        }
        return ret
    }

    override fun registerCommandAlias(oldId: String, newId: String): Disposable {
        return registerCommand(oldId) { services, context ->
            services.getService(ICommandService::class.java)?.executeCommand(newId, context)
        }
    }

    override fun getCommand(id: String): ICommand? {
        val list = commands[id]
        return list?.firstOrNull()
    }

    override fun getCommands(): Map<String, ICommand> {
        return commands.filterValues { !it.isEmpty() }.mapValues { it.value.first }
    }
}

