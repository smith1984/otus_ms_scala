package ru.beeline.vafs
package backoffice

import com.typesafe.config.ConfigFactory
import zio._
import zio.config.ReadError
import zio.config.magnolia.DeriveConfigDescriptor
import zio.config.typesafe.{TypesafeConfig, TypesafeConfigSource}

import java.io.File

package object configuration {

  case class BackOfficeConfig(backoffice: BackOffice)
  case class BackOffice(port: Int)

  import zio.config.magnolia.DeriveConfigDescriptor.descriptor

  val configDescriptor: zio.config.ConfigDescriptor[BackOfficeConfig] = descriptor[BackOfficeConfig]

  type Configuration = zio.Has[BackOfficeConfig]

  object Configuration {
    val live: Layer[ReadError[String], Configuration] =
      TypesafeConfig.fromTypesafeConfig(ConfigFactory.parseFile(new File("app.conf")).resolve(), configDescriptor)
  }

  object LoadConfig {
    def apply(configPrefix: String, path: String, secrets: String) = {
      val main    = ConfigFactory.parseFile(new File(path))
      val sec = ConfigFactory.parseFile(new File(secrets))

      if (main.hasPath(configPrefix)) {
        if (sec.hasPath(configPrefix)) {
          val config        = main.getConfig(configPrefix)
          val configSecrets = sec.getConfig(configPrefix)
          config.withFallback(configSecrets)
        } else
          main.getConfig(configPrefix)
      } else
        ConfigFactory.empty
    }
  }
}
