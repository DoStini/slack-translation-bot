package handlers

import botId
import com.slack.api.methods.MethodsClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.ActionWrapper
import model.Message

class ActionHandler(call: ApplicationCall, slack: MethodsClient, pendingTranslations: HashMap<String, String>) : Handler(
    call,
    slack,
    pendingTranslations
)   {
    override suspend fun handle(msg: Message) {
        val message = msg as ActionWrapper
        val original = pendingTranslations[message.message.ts.toString()]

        if (original == null) {
            call.respond(HttpStatusCode.InternalServerError)
            return
        }

        val action = message.actions[0]

        val response = slack.chatPostMessage {
            it
                .channel(message.container.channelId)
                .threadTs(message.message.ts)
                .text("<@${message.user.id}> ${action.text.text}: ${original.replace("<@${botId}>", "")} ")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
            pendingTranslations.remove(message.message.ts.toString())

            slack.chatDelete {
                it
                    .channel(message.container.channelId)
                    .ts(message.message.ts)
            }
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
