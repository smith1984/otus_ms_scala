package ru.beeline.vafs
package backoffice.api

import zhttp.http._
import zio.ZIO


object BackOfficeAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !! / "health" =>
        ZIO.succeed(Response.json("""{"status": "OK"}"""))

    case Method.GET -> !! / "hello" / user / _ =>
      ZIO.succeed(Response.text(s"Hello ${user} from ${sys.env("HOSTNAME")}"))

    case Method.GET -> !! / "version" =>
      ZIO.succeed(Response.json("""{"version": "0.0.3"}"""))
      }
}
