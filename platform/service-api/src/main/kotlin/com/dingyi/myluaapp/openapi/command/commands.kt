package com.dingyi.myluaapp.openapi.command

import com.intellij.openapi.util.Disposer
import com.intellij.util.concurrency.AppExecutorUtil
import java.util.concurrent.CompletableFuture

interface ICommandEvent {
    val commandId: String
    val args: Array<Any>
}

interface ICommandService {
    val onWillExecuteCommand: Event<ICommandEvent>
    val onDidExecuteCommand: Event<ICommandEvent>
    fun <T> executeCommand(commandId: String, vararg args: Any): CompletableFuture<T?>
}

interface ICommandHandler {
    operator fun invoke(accessor: ServicesAccessor, vararg args: Any)
}

interface ICommand {
    val id: String
    val handler: ICommandHandler
    val description: ICommandHandlerDescription?
}

interface ICommandHandlerDescription {
    val description: String
    val args: List<ICommandArgumentDescription>
    val returns: String?
}

interface ICommandArgumentDescription {
    val name: String
    val isOptional: Boolean
    val description: String?
    val constraint: TypeConstraint?
    val schema: IJSONSchema?
}

interface ICommandRegistry {
    val onDidRegisterCommand: Event<String>
    fun registerCommand(idOrCommand: String, handler: ICommandHandler): Disposable
    fun registerCommand(command: ICommand): Disposable
    fun registerCommandAlias(oldId: String, newId: String): Disposable
    fun getCommand(id: String): ICommand?
    fun getCommands(): Map<String, ICommand>
}

val ICommandService._serviceBrand: Unit
    get() = Unit

val CommandsRegistry: ICommandRegistry = object : ICommandRegistry {

    private val commands = mutableMapOf<String, LinkedList<ICommand>>()

    private val onDidRegisterCommand = Emitter<String>()
    override val onDidRegisterCommand: Event<String> = onDidRegisterCommand.event

    override fun registerCommand(idOrCommand: String, handler: ICommandHandler): Disposable {
        if (idOrCommand.isEmpty()) {
            throw IllegalArgumentException("invalid command")
        }
        return registerCommand(object : ICommand {
            override val id: String = idOrCommand
            override val handler: ICommandHandler = handler
            override val description: ICommandHandlerDescription? = null
        })
    }

    override fun registerCommand(command: ICommand): Disposable {
        if (command.id.isEmpty()) {
            throw IllegalArgumentException("invalid command")
        }
        if (command.description != null) {
            val constraints = command.description.args.map { it.constraint }
            val actualHandler = command.handler
            command.handler = { accessor, args ->
                validateConstraints(args, constraints)
                actualHandler(accessor, *args)
            }
        }
        val commands = commands.getOrPut(command.id) { LinkedList() }
        val removeFn = commands.unshift(command)
        val ret = Disposer.newDisposable()
        Disposer.register(ret, removeFn)
        onDidRegisterCommand.fire(command.id)
        return ret
    }

    override fun registerCommandAlias(oldId: String, newId: String): Disposable {
        return registerCommand(oldId) { accessor, args ->
            accessor.get(ICommandService).executeCommand<Any?>(newId, *args)
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

val NullCommandService: ICommandService = object : ICommandService {
    override val _serviceBrand: Unit = Unit
    override val onWillExecuteCommand: Event<ICommandEvent> = Event.None
    override val onDidExecuteCommand: Event<ICommandEvent> = Event.None
    override fun <T> executeCommand(commandId: String, vararg args: Any): CompletableFuture<T?> {
        return CompletableFuture.completedFuture(null)
    }
}

CommandsRegistry.registerCommand("noop") { }