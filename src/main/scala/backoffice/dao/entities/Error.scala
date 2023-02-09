package ru.beeline.vafs
package backoffice.dao.entities

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
case class Error(code: Int, message: String)
object Error
{
implicit val decoder: Decoder[Error] = deriveDecoder
implicit val encoder: Encoder[Error] = deriveEncoder}
