package ru.beeline.vafs
package order

import order.api.OrderAPI
import order.configuration.{Configuration, OrderConfig}
import order.dao.repositories.OrderRepository
import order.db.zioDS
import order.services.OrderService
import zio.ZIO

object Server {

  val appEnvironment = Configuration.live >+> zioDS >+> OrderRepository.live >+> OrderService.live

  val httpApp = OrderAPI.api

  val server = ZIO.service[OrderConfig].flatMap{ config =>  zhttp.service.Server.start(config.order.port, httpApp)}
    .provideCustomLayer(appEnvironment)
}
