package ru.beeline.vafs
package backoffice.services

import ru.beeline.vafs.backoffice.dao.entities.User
import ru.beeline.vafs.backoffice.dao.repositories.UserRepository
import ru.beeline.vafs.backoffice.db
import ru.beeline.vafs.backoffice.db.DataSource
import ru.beeline.vafs.backoffice.dto.UserDTO
import zio.{Has, RIO, ULayer, ZIO, ZLayer}
import zio.macros.accessible
import zio.random.Random


@accessible
object UserService {

  type UserService = Has[Service]

  type UserServiceEnv = DataSource with Random

  trait Service {

    def find(id: Long): ZIO[DataSource, Serializable, User]

    def insert(userDTO: UserDTO): ZIO[UserServiceEnv, Throwable, Unit]

    def update(id: Long, userDTO: UserDTO): ZIO[UserServiceEnv, Serializable, Unit]

    def delete(id: Long): ZIO[UserServiceEnv, Serializable, Unit]
  }

  class Impl(userRepository: UserRepository.Service) extends Service {

    val ctx = db.Ctx

    override def find(id: Long): ZIO[DataSource, Serializable, User] = for {
      user   <- userRepository.find(id).some
    } yield user

    override def update(id: Long, userDTO: UserDTO): ZIO[UserServiceEnv, Serializable, Unit] = for {
      _ <- userRepository.update(User.from(id, userDTO))
    } yield ()

    override def delete(id: Long): ZIO[UserServiceEnv, Serializable, Unit] = for {
      _ <- userRepository.delete(id)
    } yield ()

    override def insert(userDTO: UserDTO): ZIO[UserServiceEnv, Throwable, Unit] = for {
      _ <- userRepository.insert(userDTO)
    } yield ()
  }

  val live: ZLayer[UserRepository.UserRepository, Nothing, UserService.UserService] =
    ZLayer.fromService(userRepo => new Impl(userRepo))
}
