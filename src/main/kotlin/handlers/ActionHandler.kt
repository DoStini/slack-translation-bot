package handlers

import botId
import com.slack.api.methods.MethodsClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.ActionWrapper
import model.Message
import org.koin.core.component.inject
import storage.TranslationStorage
import translation.ITranslator

class ActionHandler(
    call: ApplicationCall,
    slack: MethodsClient,
    ) : Handler(
    call,
    slack,
)   {
    private val translator by inject<ITranslator>()
    private val storage by inject<TranslationStorage>()

    override suspend fun handle(msg: Message) {
        val message = msg as ActionWrapper
        val original = storage.get(message.message.ts)

        if (original == null) {
            call.respond(HttpStatusCode.InternalServerError)
            return
        }

        val action = message.actions[0]

        val textToTranslate = original.replace("<@${botId}>", "")
        val result = translator.translate(action.value, textToTranslate)

        val response = slack.chatPostMessage {
            it
                .channel(message.container.channelId)
                .threadTs(message.message.ts)
                .text("<@${message.user.id}> ${action.text.text}: $result")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
            storage.remove(message.message.ts)

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
