package ru.beeline.vafs
package backoffice.api

import zhttp.http._
import zio.ZIO


object BackOfficeAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !! / "health" =>
        ZIO.succeed(Response.json("""{"status": "OK"}"""))
      }
}
