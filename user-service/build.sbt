name := "user-service"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
libraryDependencies += "com.google.protobuf" % "protobuf-java-util" % "3.7.1"


javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")