package services

import generated.product_service
import generated.product_service.{ProductList, ProductRequest, ProductServiceGrpc, ProductsRequest}

import scala.concurrent.Future

class ProductService extends ProductServiceGrpc.ProductService {
  override def getProduct(request: ProductRequest): Future[product_service.Product] = {
    Future.successful(product_service.Product(1,"nombre", "description"))
  }

  override def getProducts(request: ProductsRequest): Future[ProductList] = {
    Future.successful(
      ProductList(
        List(product_service.Product(1,"nombre", "description")
        )
      )
    )
  }
}
