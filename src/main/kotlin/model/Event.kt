package model

import kotlinx.serialization.Serializable

open class Message

@Serializable
open class Event (
    val token: String? = null,
    val challenge: String? = null,
    val type: String? = null,
    val user: String? = null,
    val text: String? = null,
    val ts: String? = null,
    val channel: String? = null,
    val event: Event? = null,
    val event_ts: String? = null,
) {
    fun parseMessage(): Message? {
        return when(type) {
            "url_verification" -> Challenge(this)
            "app_mention" -> Mention(this)
            else -> null
        }
    }

    override fun toString(): String {
        return "Event(token=$token, challenge=$challenge, type=$type, user=$user, text=$text, ts=$ts, channel=$channel, event_ts=$event_ts)"
    }
}
