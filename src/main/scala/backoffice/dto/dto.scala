package ru.beeline.vafs
package backoffice

package object dto {
  case class UserDTO(
    username: Option[String],
    firstName: Option[String],
    lastName: Option[String],
    email: Option[String],
    phone: Option[String]
  )
}
