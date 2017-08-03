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
import sbt.Keys._
import sbt.AutoPlugin
import sbt.plugins.JvmPlugin
import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

object ScalaTIKZBuild extends AutoPlugin {

  println {
    """
      |    ____         __    ____________ ______
      |   / __/______ _/ /__ /_  __/  _/ //_/_  /
      |  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
      | /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
    """.stripMargin
  }

  override def requires: Plugins = JvmPlugin && JavaAppPackaging
  override def trigger: PluginTrigger = allRequirements

  override def projectSettings: Seq[Setting[_]] = settings

  val javaVersion: Double = sys.props("java.specification.version").toDouble

  private lazy val settings: Seq[Setting[_]] = {
    if (javaVersion < 1.8)
      sys.error("Java 8 or higher is required for building Optimus.")
    else {
      println(s"[info] Loading settings for Java $javaVersion or higher.")
      commonSettings ++ ScalaSettings ++ JavaSettings ++ PackagingOptions
    }
  }

  private val commonSettings: Seq[Setting[_]] = Seq(

    name := "ScalaTIKZ",
    organization := "com.github.vagmcs",
    description := "A plot library for Scala",
    scalaVersion := "2.11.8",

    autoScalaLibrary := true,
    managedScalaInstance := true,

    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },

    // fork a new JVM for 'run' and 'test:run'
    fork := true,

    // fork a new JVM for 'test:run', but not 'run'
    fork in Test := true,

    // add a JVM option to use when forking a JVM for 'run'
    javaOptions += "-Xmx2G",

    resolvers ++= Seq(
      Resolver.mavenLocal,
      Resolver.typesafeRepo("releases"),
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),

    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ),

    dependencyOverrides ++= Set(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )

  private lazy val PackagingOptions: Seq[Setting[_]] = Seq(

    // Include bash scripts in the 'bin' directory
    mappings in Universal ++= {
      val scriptsDir = file("scripts/")
      scriptsDir.listFiles.toSeq.map { f =>
        f -> ("bin/" + f.getName)
      }
    },

    // Include logger configuration file to the final distribution
    mappings in Universal ++= {
      val scriptsDir = file("src/main/resources/")
      scriptsDir.listFiles.toSeq.map { f =>
        f -> ("etc/" + f.getName)
      }
    },

    // File name of the universal distribution
    packageName in Universal := s"${name.value}-${version.value}"
  )

  private lazy val JavaSettings: Seq[Setting[_]] = Seq(
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),

    javaOptions ++= Seq(
      "-XX:+DoEscapeAnalysis",
      "-XX:+UseFastAccessorMethods",
      "-XX:+OptimizeStringConcat",
      "-Dlogback.configurationFile=src/main/resources/logback.xml")
  )

  private lazy val ScalaSettings: Seq[Setting[_]] = Seq(
    scalacOptions ++= Seq(
      "-Yclosure-elim",
      "-Yinline",
      "-Xno-uescape",
      "-feature",
      "-target:jvm-1.8",
      "-language:implicitConversions",
      "-Ybackend:GenBCode"
    )
  )
}
