ThisBuild / version := "0.0.5"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name             := "otus_ms_scala",
    idePackagePrefix := Some("ru.beeline.vafs")
  )

lazy val PostgresVersion  = "42.5.0"
lazy val logbackVersion   = "1.4.4"
lazy val ZioVersion       = "1.0.4"
lazy val CirceVersion     = "0.14.2"
lazy val ZIOHttpVersion   = "1.0.0.0-RC27"
lazy val QuillVersion     = "3.12.0"
lazy val LiquibaseVersion = "4.19.0"
lazy val snakeYamlVersion = "1.33"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"                 % ZioVersion,
  "dev.zio"       %% "zio-macros"          % ZioVersion,
  "dev.zio"       %% "zio-config"          % ZioVersion,
  "dev.zio"       %% "zio-config-magnolia" % ZioVersion,
  "dev.zio"       %% "zio-config-typesafe" % ZioVersion,

  "io.getquill"   %% "quill-jdbc-zio"      % QuillVersion,

  "io.d11"        %% "zhttp"               % ZIOHttpVersion,

  "org.postgresql" % "postgresql"          % PostgresVersion,

  "io.circe"      %% "circe-parser"        % CirceVersion,
  "io.circe"      %% "circe-generic"       % CirceVersion,

  "ch.qos.logback" % "logback-classic"     % logbackVersion
)


ThisBuild / assemblyMergeStrategy  := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("module-info.class") => MergeStrategy.discard
  case x if x.endsWith("/module-info.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

scalacOptions += "-Ymacro-annotations"
