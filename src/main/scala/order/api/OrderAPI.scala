package ru.beeline.vafs
package order.api

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import ru.beeline.vafs.order.dao.entities.Error
import ru.beeline.vafs.order.dto.OrderDTO
import ru.beeline.vafs.order.services.OrderService
import zhttp.http._
import zio.ZIO

object OrderAPI {

  val api = Http.collectZIO[Request] {
    case Method.GET -> !! / "health" =>
      ZIO.succeed(Response.json("""{"status": "OK"}"""))

    case Method.GET -> !! / "version" =>
      ZIO.succeed(Response.json("""{"version": "0.0.1"}"""))

    case req @ Method.GET -> !! / "api" / "v1" / "orders" =>
      OrderService
        .findAll()
        .foldM(
          err => ZIO.succeed(Response.json(Error(1, err.toString).asJson.noSpaces)),
          result => ZIO.succeed(Response.json(result.asJson.noSpaces))
        )

    case req @ Method.POST -> !! / "api" / "v1" / "order" =>
      (for {
        r     <- req.bodyAsString

        _ <- ZIO.succeed(req.toString()).debug
        dto    <- ZIO.fromEither(decode[OrderDTO](r))
        result <- OrderService.insert(dto, req.headerValue("x-request-id").getOrElse(""))
      } yield result).foldM(
        err => ZIO.succeed(Response.json(Error(2, err.toString).asJson.noSpaces)),
        result => ZIO.succeed(Response.json(s"""{"orderId": "${result}"}""")).debug
      )

  }
}
