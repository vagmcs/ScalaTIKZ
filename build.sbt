addCommandAlias("build", ";headerCreate;compile;test;package")
addCommandAlias("rebuild", ";clean;build")

useGpg := true

sonatypeProfileName := "com.github.vagmcs"

lazy val scalaTIKZ = Project("ScalaTIKZ", file("."))
  .enablePlugins(JavaAppPackaging, AutomateHeaderPlugin)
  .settings(logLevel in Test := Level.Info)
  .settings(logLevel in Compile := Level.Error)
  .settings(libraryDependencies ++= Dependencies.Logging)
  .settings(libraryDependencies ++= Dependencies.ScalaTest)
  .settings(libraryDependencies ++= Dependencies.Commons)