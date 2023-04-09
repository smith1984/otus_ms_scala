package ru.beeline.vafs
package order

import com.zaxxer.hikari.HikariDataSource
import io.getquill.{Escape, JdbcContextConfig, Literal, NamingStrategy, PostgresZioJdbcContext}
import io.getquill.context.ZioJdbc
import order.configuration.LoadConfig
import zio._

package object db {

  type DataSource = Has[javax.sql.DataSource]
  object Ctx extends PostgresZioJdbcContext(NamingStrategy(Escape, Literal))

  def hikariDS: HikariDataSource = new JdbcContextConfig(LoadConfig("order.db", "app.conf", "secrets.conf")).dataSource

  val zioDS: ZLayer[Any, Throwable, DataSource] = ZioJdbc.DataSourceLayer.fromDataSource(hikariDS)
}
