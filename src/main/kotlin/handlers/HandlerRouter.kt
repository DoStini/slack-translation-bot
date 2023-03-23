package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import model.Event

class HandlerRouter {
    fun handler(
        call: ApplicationCall,
        slack: MethodsClient,
        message: Event,
        pendingTranslations: HashMap<String, String>
    ): Handler {
        return when(message.type) {
            "url_verification" -> ChallengeHandler(call, slack, pendingTranslations)
            "app_mention" -> MentionHandler(call, slack, pendingTranslations)
            "event_callback" -> message.event?.let { handler(call, slack, it, pendingTranslations) } ?: kotlin.run {
                throw Error("Invalid specification") }
            else -> throw Error("Invalid specification")
        }
    }
}
