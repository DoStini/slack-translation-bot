package model

import kotlinx.serialization.Serializable

@Serializable
class ActionText (
    val text: String
)

@Serializable
class Action (
    val value: String,
    val action_ts: String,
    val text: ActionText,
    var messageTs: String? = null,
    var channelId: String? = null,
    var userId: String? = null
) : Message()
