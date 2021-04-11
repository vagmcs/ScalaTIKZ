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
    final val ScalaLogging = "3.9.3"

    final val ScalaTest = "3.2.7"
    final val ScalaCheck = "1.15.3"

    final val ScalaOpt = "3.7.1"
    final val Ghost4J = "1.0.1"
    final val CSVParser = "2.9.1"
    final val Enums = "1.6.1"
  }

  // Logging using slf4j and logback
  lazy val Logging: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % v.Logback,
    "com.typesafe.scala-logging" %% "scala-logging" % v.ScalaLogging
  )

  // ScalaTest and ScalaMeter for UNIT testing
  lazy val ScalaTest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % v.ScalaTest % "test",
    "org.scalacheck" %% "scalacheck" % v.ScalaCheck % "test"
  )

  // Libraries for option parsing, csv parsing and ghost-script
  lazy val Commons: Seq[ModuleID] = Seq(
    "com.github.scopt" %% "scopt" % v.ScalaOpt,
    "org.ghost4j" % "ghost4j" % v.Ghost4J,
    "com.univocity" % "univocity-parsers" % v.CSVParser,
    "com.beachape" %% "enumeratum" % v.Enums
  )
}
