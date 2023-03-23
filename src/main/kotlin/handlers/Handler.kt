package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import model.ActionWrapper
import model.Message
import org.koin.core.component.KoinComponent

abstract class Handler(
    protected val call: ApplicationCall,
    protected val slack: MethodsClient,
) : KoinComponent {
    abstract suspend fun handle(msg: Message)
}
