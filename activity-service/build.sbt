
val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  "mysql" % "mysql-connector-java" % "8.0.15",
  "io.etcd" % "jetcd-core" % "0.3.0",
  "com.typesafe.akka" %% "akka-actor" % "2.5.22" //para el scheduler
)

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)
