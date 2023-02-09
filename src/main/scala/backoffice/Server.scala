package ru.beeline.vafs
package backoffice

import backoffice.api.BackOfficeAPI

import ru.beeline.vafs.backoffice.configuration.{BackOfficeConfig, Configuration}
import ru.beeline.vafs.backoffice.dao.repositories.UserRepository
import ru.beeline.vafs.backoffice.db.zioDS
import ru.beeline.vafs.backoffice.services.UserService
import zio.ZIO

object Server {

  val appEnvironment = Configuration.live >+> zioDS >+> UserRepository.live >+> UserService.live

  val httpApp = BackOfficeAPI.api

  val server = ZIO.service[BackOfficeConfig].flatMap{ config =>  zhttp.service.Server.start(config.backoffice.port, httpApp)}
    .provideCustomLayer(appEnvironment)
}
