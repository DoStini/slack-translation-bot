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
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.request.receiveText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import model.ActionWrapper
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
                val message = call.receive<Event>()

                val handler = message.let { router.handler(call, slack, it, pendingTranslations) }
                message.parseMessage()?.let { handler.handle(it) }
            } catch (err: Exception) {
                println(err)
            }
        }
        post("/interactivity") {
            try {
                val json = call.receiveParameters()["payload"].toString()

                val actions = format.decodeFromString<ActionWrapper>(json)
                val handler = ActionHandler(call, slack, pendingTranslations)

                handler.handle(actions)
            } catch (err: Exception) {
                println(err)
            }
        }
    }
    install(ContentNegotiation) {
        json(json = format)
    }
}
