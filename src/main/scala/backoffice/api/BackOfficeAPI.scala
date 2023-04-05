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
import zio.ZIO.debug

object BackOfficeAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !! / "health" =>
      ZIO.succeed(Response.json("""{"status": "OK"}"""))

    case Method.GET -> !! / "hello" / user / _ =>
      ZIO.succeed(Response.text(s"Hello ${user} from ${sys.env("HOSTNAME")}"))

    case Method.GET -> !! / "version" =>
      ZIO.succeed(Response.json("""{"version": "0.0.4"}"""))

    case req @ Method.GET -> !! / "api" / "v1" / "user" / id =>
      UserService
        .find(id.toLong)
        .foldM(
          err => ZIO.succeed(Response.json(Error(1, err.toString).asJson.noSpaces)),
          result =>
            ZIO.succeed(
              if (req.headerValue("x-username") == result.username) Response.json(result.asJson.noSpaces)
              else Response.json(Error(5, "Access denied").asJson.noSpaces).setStatus(Status.Forbidden)
            )
        )

    case req @ Method.POST -> !! / "api" / "v1" / "user" =>
      (for {
        r      <- req.bodyAsString
        dto    <- ZIO.fromEither(decode[UserDTO](r))
        result <- UserService.insert(dto)
      } yield result).foldM(
        err => ZIO.succeed(Response.json(Error(2, err.toString).asJson.noSpaces)),
        result => ZIO.succeed(Response.ok)
      )

    case req @ Method.PUT -> !! / "api" / "v1" / "user" / id =>
      (for {
        r    <- req.bodyAsString
        dto  <- ZIO.fromEither(decode[UserDTO](r))
        verif = req.headerValue("x-username") == dto.username
        _    <- if (verif) UserService.update(id.toLong, dto) else ZIO.fail("Access denied")
      } yield ()).foldM(
        err =>
          ZIO.succeed(
            Response.json(Error(if (err.toString == "Access denied") 5 else 3, err.toString).asJson.noSpaces)
          ),
        result => ZIO.succeed(Response.ok)
      )

    case req @ Method.DELETE -> !! / "api" / "v1" / "user" / id =>
      (for {
        user <- UserService.find(id.toLong)
        verif = req.headerValue("x-username") == user.username
        _    <- if (verif) UserService.delete(id.toLong) else ZIO.fail("Access denied")
      } yield ())
        .foldM(
          err =>
            ZIO.succeed(
              Response.json(Error(if (err.toString == "Access denied") 5 else 4, err.toString).asJson.noSpaces)
            ),
          result => ZIO.succeed(Response.status(Status.NoContent))
        )

  }
}
