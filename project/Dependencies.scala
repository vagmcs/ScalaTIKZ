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
    final val Logback = "1.2.10"
    final val ScalaLogging = "3.9.4"

    final val ScalaTest = "3.2.11"
    final val ScalaCheck = "1.15.4"

    final val ScalaOpt = "3.7.1"
    final val PDFBox = "2.0.24"
    final val CSVParser = "2.9.1"
    final val Enums = "1.7.0"
    final val Ammonite = "0.11.2"
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
    "org.apache.pdfbox" % "pdfbox-tools" % v.PDFBox,
    "com.univocity" % "univocity-parsers" % v.CSVParser,
    "com.beachape" %% "enumeratum" % v.Enums,
    "sh.almond" %% "scala-kernel-api" % v.Ammonite % Provided cross CrossVersion.full
  )
}
