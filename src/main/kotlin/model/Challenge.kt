package model

import kotlinx.serialization.Serializable

@Serializable
class Challenge (
    val token: String,
    val challenge: String
): Message() {
    constructor(message: Event) : this(
        message.token!!,
        message.challenge!!
    )
}

@Serializable
class ChallengeResponse (
    val challenge: String,
)
