package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import model.ActionWrapper
import model.Message

abstract class Handler(
    protected val call: ApplicationCall,
    protected val slack: MethodsClient,
    protected val pendingTranslations: HashMap<String, String>
) {
    abstract suspend fun handle(msg: Message)
}
