package model

import kotlinx.serialization.Serializable

@Serializable
class ReactionAdded (
    val user: String,
    val reaction: String,
    val itemType: String,
    val channelId: String,
    val ts: String
) : Message() {
    constructor(event: Event) : this(
        event.user ?: throw Error("Invalid Specification"),
        event.reaction ?: throw Error("Invalid Specification"),
        event.item?.type ?: throw Error("Invalid Specification"),
        event.item?.channel ?: throw Error("Invalid Specification"),
        event.item?.ts ?: throw Error("Invalid Specification")
    )
}
