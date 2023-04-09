package ru.beeline.vafs
package order.services

import ru.beeline.vafs.order.dao.entities.Order
import ru.beeline.vafs.order.dao.repositories.OrderRepository
import ru.beeline.vafs.order.db
import ru.beeline.vafs.order.db.DataSource
import ru.beeline.vafs.order.dto.OrderDTO
import zio.macros.accessible
import zio.random.Random
import zio.{Has, ZIO, ZLayer}

import java.sql.SQLException


@accessible
object OrderService {

  type OrderService = Has[Service]

  type OrderServiceEnv = DataSource with Random

  trait Service {

    def findAll(): ZIO[DataSource, SQLException, List[Order]]

    def insert(orderDTO: OrderDTO, requestId: String): ZIO[OrderServiceEnv, Throwable, Long]
  }

  class Impl(orderRepository: OrderRepository.Service) extends Service {

    val ctx = db.Ctx

    override def findAll(): ZIO[DataSource, SQLException, List[Order]] = for {
      orders   <- orderRepository.findAll()
    } yield orders

    override def insert(orderDTO: OrderDTO, requestId: String): ZIO[OrderServiceEnv, Throwable, Long] = for {
      id <- orderRepository.insert(orderDTO, requestId)
    } yield (id)
  }

  val live: ZLayer[OrderRepository.OrderRepository, Nothing, OrderService.OrderService] =
    ZLayer.fromService(orderRepo => new Impl(orderRepo))
}
