package model

import kotlinx.serialization.Serializable
import okhttp3.Challenge

@Serializable
class Message (
    val token: String?,
    val challenge: String?,
    val type: String?,
)

@Serializable
class ChallengeResponse (
    val challenge: String,
)
