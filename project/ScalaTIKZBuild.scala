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
import sbt.Keys._
import sbt.AutoPlugin
import sbt.plugins.JvmPlugin
import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import ohnosequences.sbt.GithubRelease.keys._

object ScalaTIKZBuild extends AutoPlugin {

  private val logger = ConsoleLogger()

  final val logo = """
      |   ____         __    ____________ ______
      |  / __/______ _/ /__ /_  __/  _/ //_/_  /
      | _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
      |/___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
      |
      |A PGF/TIKZ plot library for Scala.
    """.stripMargin

  logger.info(logo)

  override def requires: Plugins = JvmPlugin && JavaAppPackaging && HeaderPlugin
  override def trigger: PluginTrigger = allRequirements

  override def projectSettings: Seq[Setting[_]] = settings

  val javaVersion: Double = sys.props("java.specification.version").toDouble

  private lazy val settings: Seq[Setting[_]] = {
    logger.info(s"[info] Loading options for Java $javaVersion.")
    if (javaVersion < 1.8) sys.error("Java 8 or higher is required for building ScalaTIKZ.")
    else commonSettings ++ ScalaSettings ++ JavaSettings ++ PackagingOptions
  }

  private val commonSettings: Seq[Setting[_]] = Seq(
    ghreleaseRepoOrg := "vagmcs",
    ghreleaseTitle := { tagName => s"${name.value} $tagName" },
    name := "ScalaTIKZ",
    organization := "com.github.vagmcs",
    description := "A plot library for Scala",
    maintainer := "Evangelos Michelioudakis",
    headerLicense := Some(HeaderLicense.Custom(logo)),
    headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cStyleBlockComment),
    scalaVersion := "3.5.0",
    crossScalaVersions := Seq("3.5.0", "2.13.14", "2.12.20"),
    autoScalaLibrary := true,
    managedScalaInstance := true,
    publishMavenStyle := true,
    Test / publishArtifact := false,
    pomIncludeRepository := { _ => false },

    // fork a new JVM for 'run' and 'test:run'
    fork := true,

    // fork a new JVM for 'test:run', but not 'run'
    Test / fork := true,
    resolvers ++= Seq(
      Seq(
        Resolver.mavenLocal,
        Resolver.typesafeRepo("releases"),
        "jitpack" at "https://jitpack.io"
      ),
      Resolver.sonatypeOssRepos("releases"),
      Resolver.sonatypeOssRepos("snapshots")
    ).flatten,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },

    // Information required in order to sync in Maven Central
    pomExtra :=
      <url>https://github.com/vagmcs</url>
        <licenses>
          <license>
            <name>GNU Lesser General Public License v3.0</name>
            <url>https://www.gnu.org/licenses/lgpl-3.0.en.html</url>
          </license>
        </licenses>
        <developers>
          <developer>
            <id>vagmcs</id>
            <name>Evangelos Michelioudakis</name>
            <url>http://users.iit.demokritos.gr/~vagmcs/</url>
          </developer>
        </developers>
  )

  private lazy val PackagingOptions: Seq[Setting[_]] = Seq(
    // Include logger configuration file to the final distribution
    Universal / mappings ++= {
      val scriptsDir = file("src/main/resources/")
      scriptsDir.listFiles.toSeq.map(f => f -> ("etc/" + f.getName))
    },

    // File name of the universal distribution
    Universal / packageName := s"${name.value}-${version.value}"
  )

  private lazy val JavaSettings: Seq[Setting[_]] = Seq(
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    javaOptions ++= Seq(
      "-XX:+DoEscapeAnalysis",
      "-XX:+OptimizeStringConcat",
      "-Dlogback.configurationFile=src/main/resources/logback.xml"
    )
  )

  private lazy val ScalaSettings: Seq[Setting[_]] = Seq(
    scalacOptions := {
      scalaBinaryVersion.value match {

        case "2.12" | "2.13" =>
          // Scala compiler options for Scala 2.12.x and 2.13.x
          Seq(
            "-deprecation", // Emit warning and location for usages of deprecated APIs.
            "-unchecked", // Enable additional warnings where generated code depends on assumptions.
            "-feature", // Emit warning and location for usages of features that should be imported explicitly.
            "-target:jvm-1.8", // Target JVM version 1.8.
            "-language:implicitConversions"
          )

        case "3" =>
          // Scala compiler settings for Scala 3.x
          Seq(
            "-deprecation", // Emit warning and location for usages of deprecated APIs.
            "-unchecked", // Enable additional warnings where generated code depends on assumptions.
            "-feature", // Emit warning and location for usages of features that should be imported explicitly.
            "-language:implicitConversions"
          )
        case _ => sys.error(s"Unsupported version of Scala '${scalaBinaryVersion.value}'")
      }
    }
  )
}
