/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A plot library for Scala.
 *
 */

import sbt._

object Dependencies {

  object v {
    final val Logback = "1.2.3"
    final val ScalaLogging = "3.7.2"

    final val ScalaTest = "3.0.1"
    final val ScalaCheck = "1.13.4"

    final val ScalaOpt = "3.7.0"
    final val Ghost4J = "1.0.0"
    final val CSVParser = "2.7.2"
    final val NameOf = "1.0.3"
  }

  // Logging using slf4j and logback
  lazy val Logging = Seq(
    "ch.qos.logback" % "logback-classic" % v.Logback,
    "com.typesafe.scala-logging" %% "scala-logging" % v.ScalaLogging
  )

  // ScalaTest and ScalaMeter for UNIT testing
  lazy val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % v.ScalaTest % "test",
    "org.scalacheck" %% "scalacheck" % v.ScalaCheck % "test"
  )

  // Libraries for option parsing, csv parsing and ghost-script
  lazy val Commons = Seq(
    "com.github.scopt" %% "scopt" % v.ScalaOpt,
    "org.ghost4j" % "ghost4j" % v.Ghost4J,
    "com.univocity" % "univocity-parsers" % v.CSVParser,
    "com.github.dwickern" %% "scala-nameof" % v.NameOf % "provided"
  )
}
