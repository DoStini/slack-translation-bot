package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import model.Event

class HandlerRouter {
    fun handler(call: ApplicationCall, slack: MethodsClient, message: Event): Handler {
        return when(message.type) {
            "url_verification" -> ChallengeHandler(call, slack)
            "app_mention" -> MentionHandler(call, slack)
            else -> ChallengeHandler(call, slack)
        }
    }
}
