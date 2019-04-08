name := "product-service"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test
testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")
libraryDependencies += "com.google.protobuf" % "protobuf-java-util" % "3.7.1"


javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")
