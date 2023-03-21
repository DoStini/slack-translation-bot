package model

import kotlinx.serialization.Serializable

open class Message

@Serializable
open class GeneralMessage (
    val token: String?,
    val challenge: String?,
    val type: String?,
) {
    fun parseMessage(): Message {
        return when(type) {
            "url_verification" -> Challenge(token!!, challenge!!)
            else -> Message()
        }
    }
}
