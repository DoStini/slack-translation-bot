package handlers

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.kotlin_extension.request.chat.blocks
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.Mention
import model.Message

class MentionHandler(call: ApplicationCall, slack: MethodsClient, pendingTranslations: HashMap<String, String>) :
    Handler(call, slack, pendingTranslations) {
    override suspend fun handle(msg: Message) {
        val message = msg as Mention

        val response = slack.chatPostMessage {
            it
                .channel(message.channel)
                .blocks {
                    section {
                        markdownText("Select a language")
                    }
                    divider()
                    actions {
                        button {
                            text(":flag-pt:", emoji = true)
                            value("pt")
                        }
                        button {
                            text(":flag-england:", emoji = true)
                            value("en")
                        }
                    }
                }
                .threadTs(message.ts)
                .text("Replying!")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
            pendingTranslations[response.ts.toString()] = message.text
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
