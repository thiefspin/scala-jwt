
ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "com.thiefspin"
ThisBuild / organizationName := "thiefspin"
ThisBuild / crossScalaVersions := Seq("2.11.12", "2.12.13", "2.13.4")

ThisBuild / scalacOptions ++= Seq(
  "-Ywarn-dead-code",
  "-Xlint:inaccessible",
  "-Ywarn-unused",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions"
)

lazy val root = (project in file("."))
  .settings(
    name := "scala-jwt",
    libraryDependencies ++= Seq(
      "commons-codec" % "commons-codec" % "1.14",
      "org.bouncycastle" % "bcprov-jdk16" % "1.46",
      "com.typesafe.play" %% "play-json" % "2.7.4" % Provided,
      "org.scalatest" %% "scalatest" % "3.2.2" % Test
    )
  )
