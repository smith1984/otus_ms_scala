package ru.beeline.vafs
package backoffice

import backoffice.api.BackOfficeAPI

object Server {

  val httpApp = BackOfficeAPI.api

  val server = zhttp.service.Server.start(8000, httpApp)
}
