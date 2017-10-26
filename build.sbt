addCommandAlias("build", ";compile;test;package")
addCommandAlias("rebuild", ";clean;build")

sonatypeProfileName := "com.github.vagmcs"

lazy val scalaTIKZ = Project("ScalaTIKZ", file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(logLevel in Test := Level.Info)
  .settings(logLevel in Compile := Level.Error)
  .settings(libraryDependencies ++= Dependencies.Logging)
  .settings(libraryDependencies ++= Dependencies.ScalaTest)
  .settings(libraryDependencies ++= Dependencies.Commons)