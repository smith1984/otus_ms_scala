ThisBuild / version := "0.0.1"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name             := "otus_ms_scala",
    idePackagePrefix := Some("ru.beeline.vafs")
  )

lazy val ZioVersion     = "1.0.4"
lazy val ZIOHttpVersion = "1.0.0.0-RC27"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio"   % ZioVersion,
  "io.d11"  %% "zhttp" % ZIOHttpVersion
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}

scalacOptions += "-Ymacro-annotations"
