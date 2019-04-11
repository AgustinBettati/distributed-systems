
import generated.product_service.ProductServiceGrpc.ProductServiceStub
import generated.user.UserServiceGrpc.UserServiceStub

import scala.util.Random

case class ClientBalancer[T](stubs: List[T]) {
  def obtainStub: T = Random.shuffle(stubs).head
}
