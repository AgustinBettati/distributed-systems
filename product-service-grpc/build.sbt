
val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
//    "com.typesafe.slick" %% "slick" % "3.2.3",
//    "org.slf4j" % "slf4j-nop" % "1.6.4",
//    "mysql" % "mysql-connector-java" % "8.0.15"

)

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)