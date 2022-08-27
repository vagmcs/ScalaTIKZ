addCommandAlias("check", ";headerCreate;dependencyUpdates;compile")
addCommandAlias("build", ";check;test;package")
addCommandAlias("rebuild", ";clean;build")

sonatypeProfileName := "com.github.vagmcs"

lazy val scalaTIKZ = Project("ScalaTIKZ", file("."))
  .enablePlugins(JavaAppPackaging, AutomateHeaderPlugin)
  .settings(Test / logLevel := Level.Info)
  .settings(Compile / logLevel := Level.Warn)
  .settings(libraryDependencies ++= Dependencies.Logging)
  .settings(libraryDependencies ++= Dependencies.ScalaTest)
  .settings(libraryDependencies ++= Dependencies.Commons)
