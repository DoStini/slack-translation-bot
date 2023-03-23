import io.ktor.server.application.Application
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import handlers.ActionHandler
import handlers.HandlerRouter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receiveParameters
import io.ktor.server.request.receiveText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import model.Action
import model.Event

val token: String = System.getenv("SLACK_TOKEN")
private val slack: MethodsClient = Slack.getInstance().methods(token)
val router: HandlerRouter = HandlerRouter()
val botId = slack.authTest {
    it.token(token)
}.userId

val format = Json {ignoreUnknownKeys = true}

fun main() {
    embeddedServer(Netty, port = 8000, module = Application::serverModule).start(wait = true)
}


fun Application.serverModule() {
    val pendingTranslations = HashMap<String, String>()
    routing {
        post("/") {
            try {
                val json = call.receiveText()

                val rawMessage = format.decodeFromString<JsonObject>(json)

                val message: Event? = rawMessage["event"]?.let {
                    format.decodeFromString(it.toString())
                } ?: run { format.decodeFromString(json) }

                val handler = message?.let { router.handler(call, slack, it, pendingTranslations) }
                message?.parseMessage()?.let { handler?.handle(it) }
            } catch (err: Exception) {
                println(err)
            }
        }
        post("/interactivity") {
            try {
                val json = call.receiveParameters()["payload"].toString()

                val rawMessage = format.decodeFromString<JsonObject>(json)
                val rawActions = rawMessage["actions"] as JsonArray
                val userId = format.decodeFromString<String>(format.decodeFromString<JsonObject>(rawMessage["user"].toString())["id"].toString())

                val rawBotMessage = format.decodeFromString<JsonObject>(rawMessage["message"].toString())
                val botMessageId = format.decodeFromString<String>(rawBotMessage["ts"].toString())

                val rawContainer = format.decodeFromString<JsonObject>(rawMessage["container"].toString())
                val channelId = format.decodeFromString<String>(rawContainer["channel_id"].toString())


                val actions: List<Action> = rawActions.let {
                    it.map { rawAction ->
                        val action = format.decodeFromString<Action>(rawAction.toString())
                        action.userId = userId
                        action.messageTs = botMessageId
                        action.channelId = channelId
                        action
                    }
                }

                val handler = ActionHandler(call, slack, pendingTranslations)

                actions.forEach { handler.handle(it) }
            } catch (err: Exception) {
                println(err)
            }
        }
    }
    install(ContentNegotiation) {
        json(json = format)
    }
}
