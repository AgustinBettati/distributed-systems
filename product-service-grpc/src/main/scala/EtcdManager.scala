import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import io.etcd.jetcd.options.PutOption
import io.etcd.jetcd.{ByteSequence, Client, KV}

import scala.concurrent.duration._
//docker run -p 2379:2379 quay.io/coreos/etcd:v3.2.0 \
//  /usr/local/bin/etcd \
//--advertise-client-urls http://0.0.0.0:2379 \
//  --listen-client-urls http://0.0.0.0:2379 \
//  --initial-advertise-peer-urls http://0.0.0.0:2380 \
//  --listen-peer-urls http://0.0.0.0:2380 \
//  --initial-cluster "default=http://0.0.0.0:2380"


object EtcdManager  {
  val client: Client = Client.builder().endpoints("http://localhost:2379").build()
  val leaseClient = client.getLeaseClient

  def registerInstanceInEtcd(port: Int, serviceName: String): Unit = {
    val client: Client = Client.builder().endpoints("http://localhost:2379").build()
    val kvClient: KV = client.getKVClient
    val key: ByteSequence = ByteSequence.from(s"services/$serviceName/$port".getBytes())

    val id = grantLease(3)
    println(s"making request to register service $serviceName in port $port")
    kvClient.put(key, ByteSequence.from(port.toString, Charset.forName("UTF-8")),PutOption.newBuilder().withLeaseId(id).build())
    keepLeaseAlive(id)
  }

  private def grantLease(ttl: Long): Long = {
    val feature = leaseClient.grant(ttl)
    val response = feature.get
    response.getID
  }

  private def keepLeaseAlive(id: Long): Unit = {
    val system = ActorSystem("mySystem")
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    system.scheduler.schedule(0 seconds, 2 seconds) {
      leaseClient.keepAliveOnce(id)
    }
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

