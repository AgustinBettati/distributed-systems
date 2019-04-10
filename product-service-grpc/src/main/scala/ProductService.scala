import generated.product_service
import generated.product_service.{ProductList, ProductRequest, ProductServiceGrpc, ProductsRequest}

import scala.concurrent.Future

class ProductService extends ProductServiceGrpc.ProductService {
  override def getProduct(request: ProductRequest): Future[product_service.Product] = {
    ProductDatabase.getProductById(request.id) match {
      case Some(ProductSchema(id,name, description)) =>
        Future.successful(product_service.Product(id,name,description))
      case None =>
        Future.failed(new RuntimeException("Not found"))
    }
  }

  override def getProducts(request: ProductsRequest): Future[ProductList] = {
    val products = ProductDatabase.getAllProducts
    Future.successful(
      ProductList(
        products.map{case ProductSchema(id,name, description) => product_service.Product(id,name,description)}
      )
    )
  }
}
