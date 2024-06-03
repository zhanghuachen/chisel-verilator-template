import mill._, scalalib._
import os.Path

object mux2_1 extends ScalaModule {
    override def millSourcePath = os.pwd
    override def scalaVersion = "2.13.12"
    override def scalacOptions = Seq(
        "-language:reflectiveCalls",
        "-deprecation",
        "-feature",
        "-Xcheckinit",
    )

    override def ivyDeps = Agg(
        ivy"org.chipsalliance::chisel:6.2.0",
    )

    override def scalacPluginIvyDeps = Agg(
        ivy"org.chipsalliance:::chisel-plugin:6.2.0",
    )

}
