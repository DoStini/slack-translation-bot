package model

import kotlinx.serialization.Serializable

@Serializable
class Mention (
    val user: String,
    val text: String,
    val ts: String,
    val channel: String,
    val event_ts: String
): Message() {
    constructor(message: Event) : this(
        message.user!!,
        message.text!!,
        message.ts!!,
        message.channel!!,
        message.eventTs!!
    )
}
