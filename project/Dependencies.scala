/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
 */

import sbt._

object Dependencies {

  final val LogbackVersion = "1.2.3"
  final val ScalaLogging = "3.7.2"
  final val ScalaTestVersion = "3.0.1"
  final val ScalaCheckVersion = "1.13.4"
  final val ScalaOptVersion = "3.6.0"
  final val GhostVersion = "1.0.0"
  final val CSVParserVersion = "2.5.1"
  final val NameOfVersion = "1.0.3"

  // Logging using slf4j and logback
  lazy val Logging = Seq(
    "ch.qos.logback" % "logback-classic" % LogbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % ScalaLogging
  )

  // ScalaTest and ScalaMeter for UNIT testing
  lazy val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % ScalaTestVersion % "test",
    "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % "test"
  )

  // Libraries for option parsing, csv parsing and ghost-script
  lazy val Commons = Seq(
    "com.github.scopt" %% "scopt" % ScalaOptVersion,
    "org.ghost4j" % "ghost4j" % GhostVersion,
    "com.univocity" % "univocity-parsers" % CSVParserVersion,
    "com.github.dwickern" %% "scala-nameof" % NameOfVersion % "provided"
  )
}
