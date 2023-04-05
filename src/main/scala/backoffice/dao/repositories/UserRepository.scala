package ru.beeline.vafs
package backoffice.dao.repositories

import io.getquill.context.ZioJdbc._
import backoffice.dao.entities._
import backoffice.db

import ru.beeline.vafs.backoffice.dto.UserDTO
import zio.{Has, ULayer, ZLayer}

object UserRepository {
  val ctx = db.Ctx
  import ctx._

  type UserRepository = Has[Service]

  trait Service{
    def find(id: Long): QIO[Option[User]]

    def insert(userDTO: UserDTO): QIO[Unit]

    def update(user: User): QIO[Unit]

    def delete(id: Long): QIO[Unit]
  }

  class Impl extends Service {

    val userSchema = quote{
      querySchema[User](""""User"""")
    }

    override def find(id: Long): QIO[Option[User]] = ctx.run(
      userSchema.filter(_.id == lift(id)).take(1)
    ).map(_.headOption)

    override def insert(userDTO: UserDTO): QIO[Unit] = ctx.run(
      userSchema.insert(lift(User.from(0, userDTO))).returningGenerated(_.id)
    ).unit

    override def update(user: User): QIO[Unit] = ctx.run(
      userSchema.filter(_.id == lift(user.id))
        .update(lift(user))
    ).unit

    override def delete(id: Long): QIO[Unit] = ctx.run(
      userSchema.filter(_.id == lift(id))
        .delete
    ).unit
  }

  val live: ULayer[UserRepository] = ZLayer.succeed(new Impl)
}
