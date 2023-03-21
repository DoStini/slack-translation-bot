package handlers

import io.ktor.server.application.ApplicationCall
import model.GeneralMessage

class HandlerRouter {
    fun handler(call: ApplicationCall, message: GeneralMessage): Handler {
        return when(message.type) {
            "url_verification" -> ChallengeHandler(call)
            else -> ChallengeHandler(call)
        }
    }
}
