import io.ktor.server.application.Application
import com.slack.api.Slack
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import model.ChallengeResponse
import model.Message

val token: String = System.getenv("SLACK_TOKEN")
val slack: Slack = Slack.getInstance()

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8000, module = Application::serverModule).start(wait = true)
}



fun Application.serverModule() {
    routing {
        post("/") {
            val challenge = call.receive<Message>()
            call.respond(ChallengeResponse(challenge.challenge!!))
        }
    }
    install(ContentNegotiation) {
        json()
    }
}
