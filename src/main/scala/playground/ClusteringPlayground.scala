package playground

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object ClusteringPlayground {

  def main(args: Array[String]): Unit = {
    (2551 to 2553).foreach(startNode)
  }

  def startNode(port:Int)={
    val config = ConfigFactory.parseString(
      s"""
         |akka {
         |  actor {
         |    provider = cluster
         |  }
         |
         |  remote {
         |    artery {
         |      enabled = on
         |      transport = aeron-udp
         |      canonical.hostname = "localhost"
         |      canonical.port = $port
         |    }
         |  }
         |
         |  cluster {
         |    seed-nodes = ["akka://RTJVMCluster@localhost:2551", "akka://RTJVMCluster@localhost:2552"]
         |  }
         |}
       """.stripMargin)
    ActorSystem("TestCluster", config)
  }
}
