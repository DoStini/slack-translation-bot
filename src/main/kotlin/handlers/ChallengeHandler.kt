package handlers

import com.slack.api.methods.MethodsClient
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.ActionWrapper
import model.Challenge
import model.ChallengeResponse
import model.Message

class ChallengeHandler(call: ApplicationCall, slack: MethodsClient) : Handler(call, slack) {
    override suspend fun handle(msg: Message) {
        val message = msg as Challenge

        val challengeResponse = ChallengeResponse(message.challenge)
        call.respond(challengeResponse)
    }
}
