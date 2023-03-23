package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActionText (
    val text: String
)

@Serializable
class ActionMessage(
    val ts: String,
)

@Serializable
class ActionUser(
    val id: String
)

@Serializable
class ActionWrapper(
    val user: ActionUser,
    val message: ActionMessage,
    val container: ActionContainer,
    val actions: List<Action>
) : Message()

@Serializable
class ActionContainer (
    @SerialName("channel_id")
    val channelId: String,
)

@Serializable
class Action(
    val value: String,
    val text: ActionText,
) : Message()
