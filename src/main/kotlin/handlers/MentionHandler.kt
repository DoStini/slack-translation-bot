package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.Mention
import model.Message

class MentionHandler(call: ApplicationCall, slack: MethodsClient) : Handler(call, slack) {
    override suspend fun handle(msg: Message) {
        val message = msg as Mention

        val response = slack.chatPostMessage {
            it
                .channel(message.channel)
                .threadTs(message.ts)
                .text("Replying!")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
