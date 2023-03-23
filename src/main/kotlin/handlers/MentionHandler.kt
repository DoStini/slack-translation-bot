package handlers

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.kotlin_extension.request.chat.blocks
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.ActionWrapper
import model.Mention
import model.Message
import org.koin.core.component.inject
import translation.ITranslator

class MentionHandler(call: ApplicationCall, slack: MethodsClient, pendingTranslations: HashMap<String, String>) :
    Handler(call, slack, pendingTranslations) {

    private val translator by inject<ITranslator>()

    override suspend fun handle(msg: Message) {
        val message = msg as Mention

        val response = slack.chatPostMessage {
            it
                .channel(message.channel)
                .blocks {
                    section {
                        markdownText("<@${message.user}> Select a language")
                    }
                    divider()
                    actions {
                        translator.languages().forEach {
                            button {
                                text(it.emoji, emoji = true)
                                value(it.value)
                            }
                        }
                    }
                }
                .threadTs(message.ts)
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
            pendingTranslations[response.ts.toString()] = message.text
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
