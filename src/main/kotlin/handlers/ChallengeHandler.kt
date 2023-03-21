package handlers

import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import model.Challenge
import model.ChallengeResponse
import model.GeneralMessage
import model.Message

class ChallengeHandler(call: ApplicationCall) : Handler(call) {
    override suspend fun handle(msg: Message) {
        val message = msg as Challenge

        val challengeResponse = ChallengeResponse(message.challenge)
        call.respond(challengeResponse)
    }
}
