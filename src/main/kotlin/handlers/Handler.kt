package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import model.ActionWrapper
import model.Message
import org.koin.core.component.KoinComponent

abstract class Handler(
    protected val call: ApplicationCall,
    protected val slack: MethodsClient,
    protected val pendingTranslations: HashMap<String, String>
) : KoinComponent {
    abstract suspend fun handle(msg: Message)
}
