import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import io.etcd.jetcd.{ByteSequence, Client, KV}

//docker run -p 2379:2379 quay.io/coreos/etcd:v3.2.0 \
//  /usr/local/bin/etcd \
//--advertise-client-urls http://0.0.0.0:2379 \
//  --listen-client-urls http://0.0.0.0:2379 \
//  --initial-advertise-peer-urls http://0.0.0.0:2380 \
//  --listen-peer-urls http://0.0.0.0:2380 \
//  --initial-cluster "default=http://0.0.0.0:2380"


object EtcdManager  {

  def registerInstanceInEtcd(port: Int, serviceName: String): Unit = {
    val client: Client = Client.builder().endpoints("http://localhost:2379").build()
    val kvClient: KV = client.getKVClient
    val key: ByteSequence = ByteSequence.from(s"services/$serviceName/$port".getBytes())
    kvClient.put(key, ByteSequence.from(port.toString, Charset.forName("UTF-8")))
  }

  def main(args: Array[String]): Unit = {
    registerInstanceInEtcd(9009, "product")
  }
}

object EtcdLock  {

  val client: Client = Client.builder().endpoints("http://localhost:2379").build()
  val lockClient = client.getLockClient
  val leaseClient = client.getLeaseClient

  def main(args: Array[String]): Unit = {
    val leaseId = grantLease(20)
    println(lockClient.lock(ByteSequence.from(s"master-instance".getBytes()), leaseId).get(1000, TimeUnit.MILLISECONDS))
    println("se asigno el primero")


    println("pase de largo")
    val sndLeaseId = grantLease(20)
    println(lockClient.lock(ByteSequence.from(s"master-instance".getBytes()), sndLeaseId).get(1000, TimeUnit.MILLISECONDS))
  }


  private def grantLease(ttl: Long): Long = {
    val feature = leaseClient.grant(ttl)
    val response = feature.get
    response.getID
  }
}

