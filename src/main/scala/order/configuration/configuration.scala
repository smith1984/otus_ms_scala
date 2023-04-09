package ru.beeline.vafs
package order

import com.typesafe.config.ConfigFactory
import zio._
import zio.config.ReadError
import zio.config.typesafe.TypesafeConfig

import java.io.File

package object configuration {

  case class OrderConfig(order: Order)
  case class Order(port: Int)

  import zio.config.magnolia.DeriveConfigDescriptor.descriptor

  val configDescriptor: zio.config.ConfigDescriptor[OrderConfig] = descriptor[OrderConfig]

  type Configuration = zio.Has[OrderConfig]

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
