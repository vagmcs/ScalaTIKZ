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
    final val LogbackVersion = "1.2.3"
    final val ScalaLogging = "3.7.2"

    final val ScalaTestVersion = "3.0.1"
    final val ScalaCheckVersion = "1.13.4"

    final val ScalaOptVersion = "3.7.0"
    final val GhostVersion = "1.0.0"
    final val CSVParserVersion = "2.7.2"
    final val NameOfVersion = "1.0.3"
  }

  // Logging using slf4j and logback
  lazy val Logging = Seq(
    "ch.qos.logback" % "logback-classic" % v.LogbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % v.ScalaLogging
  )

  // ScalaTest and ScalaMeter for UNIT testing
  lazy val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % v.ScalaTestVersion % "test",
    "org.scalacheck" %% "scalacheck" % v.ScalaCheckVersion % "test"
  )

  // Libraries for option parsing, csv parsing and ghost-script
  lazy val Commons = Seq(
    "com.github.scopt" %% "scopt" % v.ScalaOptVersion,
    "org.ghost4j" % "ghost4j" % v.GhostVersion,
    "com.univocity" % "univocity-parsers" % v.CSVParserVersion,
    "com.github.dwickern" %% "scala-nameof" % v.NameOfVersion % "provided"
  )
}
