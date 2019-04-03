name := "client-service"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")