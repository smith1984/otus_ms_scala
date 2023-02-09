package ru.beeline.vafs
package backoffice

import com.zaxxer.hikari.HikariDataSource
import io.getquill.{Escape, JdbcContextConfig, Literal, NamingStrategy, PostgresZioJdbcContext, SnakeCase}
import io.getquill.context.ZioJdbc
import ru.beeline.vafs.backoffice.configuration.LoadConfig
import zio.{Has, RIO, URIO, ZIO, ZLayer, ZManaged, _}

package object db {

  type DataSource = Has[javax.sql.DataSource]
  object Ctx extends PostgresZioJdbcContext(NamingStrategy(Escape, Literal))

  def hikariDS: HikariDataSource = new JdbcContextConfig(LoadConfig("backoffice.db", "app.conf", "secrets.conf")).dataSource

  val zioDS: ZLayer[Any, Throwable, DataSource] = ZioJdbc.DataSourceLayer.fromDataSource(hikariDS)
}
