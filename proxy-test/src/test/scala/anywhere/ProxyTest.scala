package anywhere

import java.io.{File, PrintWriter, Writer}

import ammonite.TestRepl
import utest._
import ammonite.runtime.tools.IvyThing
import coursier._

import scala.tools.nsc.interpreter.OutputStream

object ProxyTest extends TestSuite {

  val check = new TestRepl()
  val depds = Seq(
    Dependency(
      Module("com.lihaoyi", "upickle_2.11"), "0.1.12312-DOESNT-EXIST"
    )
  )

  val repositories: Seq[Repository] = Seq(
    MavenRepository("https://repo1.maven.org/maven2")
  )

  import java.io.FileOutputStream
  import java.io.PrintStream

  val logFile = new File("/tmp/test-log")
  val logOut = new PrintStream(new FileOutputStream(logFile, true))
  val tests = this {
    //    'testReplSession {
    //      System.setProperty("https.proxyHost", "10.0.0.100")
    //      System.setProperty("https.proxyPort", "65530")
    //      sys.props.filter(_._1.contains("proxy")).foreach(println)
    //      check.session(
    //        """
    //            @ import $ivy.`com.lihaoyi::upickle:0.1.12312-DOESNT-EXIST`
    //            error: Failed to resolve ivy dependencies
    //          """)
    //    }
    'IvyThingResolve {
      System.setProperty("https.proxyHost", "10.0.0.100")
      System.setProperty("https.proxyPort", "65530")

      IvyThing.resolveArtifact(repositories, depds, verbose = true)
    }
  }


  System.clearProperty("https.proxyHost")
  System.clearProperty("https.proxyPort")
}