package ru.beeline.vafs
package backoffice.dao.entities

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import backoffice.dto.UserDTO

case class User(
  id: Long,
  username: Option[String],
  firstName: Option[String],
  lastName: Option[String],
  email: Option[String],
  phone: Option[String]
)

object User {
  implicit val decoder: Decoder[User] = deriveDecoder
  implicit val encoder: Encoder[User] = deriveEncoder

  def from(id: Long, userDTO: UserDTO): User = User(
    id = id,
    username = userDTO.username,
    firstName = userDTO.firstName,
    lastName = userDTO.lastName,
    email = userDTO.email,
    phone = userDTO.phone
  )
}
