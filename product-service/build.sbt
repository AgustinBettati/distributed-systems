name := "product-service"
version := "1.0.0-SNAPSHOT"
scalaVersion := "2.12.8"

scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked")

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, ParadoxPlugin)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += javaWs
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.5.20"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test
libraryDependencies += "com.lightbend.play" %% "play-grpc-testkit" % "0.6.0" % Test

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")



import akka.grpc.gen.javadsl.play._
import com.typesafe.sbt.packager.docker.{ Cmd, CmdLike, DockerAlias, ExecCmd }

// #grpc_play_plugins
// build.sbt
lazy val `play-java-grpc-example` = (project in file("."))
  .enablePlugins(PlayEbean)
  .enablePlugins(PlayJava)
  .enablePlugins(AkkaGrpcPlugin) // enables source generation for gRPC
  .enablePlugins(PlayAkkaHttp2Support) // enables serving HTTP/2 and gRPC
  // #grpc_play_plugins
  .settings(
  akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Java),
  // #grpc_client_generators
  // build.sbt
  akkaGrpcExtraGenerators += PlayJavaClientCodeGenerator,
  // #grpc_client_generators
  // #grpc_server_generators
  // build.sbt
  akkaGrpcExtraGenerators += PlayJavaServerCodeGenerator,
  // #grpc_server_generators
  PlayKeys.devSettings ++= Seq(
    "play.server.http.port" -> "disabled",
    "play.server.https.port" -> "9443",
    // Configures the keystore to use in Dev mode. This setting is equivalent to `play.server.https.keyStore.path`
    // in `application.conf`.
    "play.server.https.keyStore.path" -> "conf/selfsigned.keystore",
  )
)
  .settings(
    // workaround to https://github.com/akka/akka-grpc/pull/470#issuecomment-442133680
    dockerBaseImage := "openjdk:8-alpine",
    dockerCommands  :=
      Seq.empty[CmdLike] ++
        Seq(
          Cmd("FROM", "openjdk:8-alpine"),
          ExecCmd("RUN", "apk", "add", "--no-cache", "bash")
        ) ++
        dockerCommands.value.tail ,
    dockerAliases in Docker += DockerAlias(None, None, "play-java-grpc-example", None),
    packageName in Docker := "play-java-grpc-example",
  )