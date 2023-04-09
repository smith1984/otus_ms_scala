package ru.beeline.vafs
package order.dao.entities

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import order.dto.OrderDTO

import java.time.LocalDateTime

case class Order(
                  id: Long,
                  number: String,
                  price: Long,
                  created_at: LocalDateTime,
                  updated_at: Option[LocalDateTime],
                  request_id: String
)

object Order {
  implicit val decoder: Decoder[Order] = deriveDecoder
  implicit val encoder: Encoder[Order] = deriveEncoder

  def from(id: Long, orderDTO: OrderDTO, requestId: String): Order = Order(
    id = id,
    number = orderDTO.number,
    price = orderDTO.price,
    created_at = orderDTO.created_at.getOrElse(LocalDateTime.now()),
    updated_at = orderDTO.updated_at,
    request_id = requestId
  )
}
