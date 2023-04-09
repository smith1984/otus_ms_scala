package ru.beeline.vafs
package order.dao.entities

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
case class Error(code: Int, message: String)
object Error
{
implicit val decoder: Decoder[Error] = deriveDecoder
implicit val encoder: Encoder[Error] = deriveEncoder}
