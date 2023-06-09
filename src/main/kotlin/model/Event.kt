package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

open class Message

@Serializable
class Item (
    val type: String?,
    val channel: String?,
    val ts: String?
)

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
    @SerialName("event_ts")
    val eventTs: String? = null,
    val reaction: String? = null,
    val item: Item? = null
) {
    fun parseMessage(): Message? {
        return when(type) {
            "url_verification" -> Challenge(this)
            "app_mention" -> Mention(this)
            "reaction_added" -> ReactionAdded(this)
            "event_callback" -> event?.parseMessage() ?: run { throw Error("Invalid Specification") }
            else -> null
        }
    }

    override fun toString(): String {
        return "Event(token=$token, challenge=$challenge, type=$type, user=$user, text=$text, ts=$ts, channel=$channel, event_ts=$eventTs)"
    }
}
