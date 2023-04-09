package ru.beeline.vafs
package order

import java.time.LocalDateTime

package object dto {
  case class OrderDTO(
                       number: String,
                       price: Long,
                       created_at: Option[LocalDateTime],
                       updated_at: Option[LocalDateTime]
  )
}
