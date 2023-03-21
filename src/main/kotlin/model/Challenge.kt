package model

import kotlinx.serialization.Serializable
import okhttp3.Challenge

@Serializable
class Challenge (
    val token: String,
    val challenge: String
): Message()

@Serializable
class ChallengeResponse (
    val challenge: String,
)
