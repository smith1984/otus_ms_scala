package ru.beeline.vafs
package order.dao.repositories

import io.getquill.context.ZioJdbc._
import order.dao.entities._
import order.db

import ru.beeline.vafs.order.dto.OrderDTO
import zio.{Has, ULayer, ZLayer}

object OrderRepository {
  val ctx = db.Ctx
  import ctx._

  type OrderRepository = Has[Service]

  trait Service {
    def findAll(): QIO[List[Order]]

    def insert(orderDTO: OrderDTO, requestId: String): QIO[Long]
  }

  class Impl extends Service {

    val orderSchema = quote {
      querySchema[Order](""""order"""")
    }

    override def findAll(): QIO[List[Order]] = ctx
      .run(
        orderSchema
      )

    override def insert(orderDTO: OrderDTO, requestId: String): QIO[Long] = ctx
      .run(
        orderSchema.insert(lift(Order.from(0, orderDTO, requestId))).returningGenerated(_.id)
      )
  }

  val live: ULayer[OrderRepository] = ZLayer.succeed(new Impl)
}
