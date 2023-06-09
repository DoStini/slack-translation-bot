package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.Message
import model.ReactionAdded
import org.koin.core.component.inject
import translation.ITranslator

class ReactionAddedHandler(call: ApplicationCall, slack: MethodsClient): Handler(call, slack) {
    private val translator by inject<ITranslator>()

    override suspend fun handle(msg: Message) {
        val message = msg as ReactionAdded

        val target = translator.languageByEmoji(message.reaction)

        if (target == null) {
            call.respond(HttpStatusCode.OK)
            return
        }
        var retrieved = slack.conversationsHistory {
            it
                .channel(message.channelId)
                .latest(message.ts)
                .inclusive(true)
                .limit(1)
        }.messages[0]

        if (retrieved.threadTs != null) {
            retrieved = slack.conversationsReplies {
                it
                    .channel(message.channelId)
                    .ts(message.ts)
            }.messages[0]
        }

        val result = translator.translate(target.value, retrieved.text)

        val response = slack.chatPostEphemeral {
            it
                .user(message.user)
                .channel(message.channelId)
                .threadTs(retrieved.threadTs)
                .text("<@${message.user}> :${target.emoji}: $result")
        }

        if (response.isOk) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}