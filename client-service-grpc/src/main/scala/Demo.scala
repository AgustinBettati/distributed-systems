import demo.msg.{Address, Person}
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object Demo extends App {

  val p1 = Person("Juan", 25, Vector(Address("Rivadavia 123", "CABA")))

//  println(p.asJson.toString().length)

  val start = System.currentTimeMillis()

  for (i <- 1 to 100000) {

//    val json = p1.asJson.toString()
    val bytes: Array[Byte] = Person.toByteArray(p1)
//    val p2 = Person.parseFrom(bytes)
  }

  val end = System.currentTimeMillis()

  println(end - start)


//  println(bytes.length)
//
//  println(p1)
//  println(p2)
}
