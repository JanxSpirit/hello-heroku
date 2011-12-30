import cc.spray._
import typeconversion.SprayJsonSupport
import http.MediaTypes._

trait MsgService extends Directives
  with SprayJsonSupport {

  val service = {
    path("test") {
      get {
        respondWithMediaType(`text/html`) {
          _.complete {
            <html>
              <h1>Test resource</h1>
            </html>
          }
        }
      }
    }
  }
} 
