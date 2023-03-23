package handlers

import botId
import com.slack.api.methods.MethodsClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.Action
import model.Message

class ActionHandler(call: ApplicationCall, slack: MethodsClient, pendingTranslations: HashMap<String, String>) : Handler(
    call,
    slack,
    pendingTranslations
)   {
    override suspend fun handle(msg: Message) {
        val message = msg as Action
        val original = pendingTranslations[message.messageTs.toString()]

        if (original == null) {
            call.respond(HttpStatusCode.InternalServerError)
            return
        }

        val response = slack.chatPostMessage {
            it
                .channel(message.channelId)
                .threadTs(message.messageTs)
                .text("<@${message.userId}> ${message.text.text}: ${original.replace("<@${botId}>", "")} ")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
            pendingTranslations.remove(message.messageTs.toString())
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
