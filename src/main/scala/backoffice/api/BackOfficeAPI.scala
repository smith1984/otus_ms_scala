package ru.beeline.vafs
package backoffice.api

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import ru.beeline.vafs.backoffice.dao.entities.Error
import ru.beeline.vafs.backoffice.dto.UserDTO
import ru.beeline.vafs.backoffice.services.UserService
import zhttp.http._
import zio.ZIO


object BackOfficeAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !! / "health" =>
        ZIO.succeed(Response.json("""{"status": "OK"}"""))

    case Method.GET -> !! / "hello" / user / _ =>
      ZIO.succeed(Response.text(s"Hello ${user} from ${sys.env("HOSTNAME")}"))

    case Method.GET -> !! / "version" =>
      ZIO.succeed(Response.json("""{"version": "0.0.4"}"""))

    case Method.GET -> !! / "api" / "v1" / "user" / id =>
      UserService.find(id.toLong).foldM(
        err => ZIO.succeed(Response.json(Error(1, err.toString).asJson.noSpaces)),
        result => ZIO.succeed(Response.json(result.asJson.noSpaces))
      )

    case req@Method.POST -> !! / "api" / "v1" / "user" =>
      (for {
        r <- req.bodyAsString
        dto <- ZIO.fromEither(decode[UserDTO](r))
        result <- UserService.insert(dto)
      } yield result).foldM(
        err => ZIO.succeed(Response.json(Error(2, err.toString).asJson.noSpaces)),
        result => ZIO.succeed(Response.ok)
      )

    case req@Method.PUT -> !! / "api" / "v1" / "user" / id => (for {
      r <- req.bodyAsString
      dto <- ZIO.fromEither(decode[UserDTO](r))
      _ <- UserService.update(id.toLong, dto)
    } yield ()).foldM(
      err => ZIO.succeed(Response.json(Error(3, err.toString).asJson.noSpaces)),
      result => ZIO.succeed(Response.ok)
    )

    case Method.DELETE -> !! / "api" / "v1" / "user" / id => UserService.delete(id.toLong).foldM(
      err => ZIO.succeed(Response.json(Error(4, err.toString).asJson.noSpaces)),
      result => ZIO.succeed(Response.status(Status.NoContent))
    )

  }
}
