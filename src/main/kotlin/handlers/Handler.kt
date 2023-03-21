package handlers

import io.ktor.server.application.ApplicationCall
import model.GeneralMessage
import model.Message

abstract class Handler (protected val call: ApplicationCall) {
    abstract suspend fun handle(msg: Message)
}
