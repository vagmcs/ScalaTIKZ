# Building and Linking ScalaTIKZ

In order to build ScalaTIKZ from source you need to have Java SE version 8 or higher and
[SBT](http://www.scala-sbt.org/) installed in your system.
 
## Instructions to build ScalaTIKZ from source

To build the ScalaTIKZ distribution, type the following command:

```
$ sbt dist
```

After a successful compilation, the distribution is located inside the `./target/universal/ScalaTIKZ-*.zip` archive.
You can extract this file and add the path/to/ScalaTIKZ-<version>/bin in your PATH, in order to execute the CLI script
from terminal (see Section [Add ScalaTIKZ executables in your default PATH](#Add-ScalaTIKZ-executables-in-your-default-PATH)). The distribution contains all library dependencies and requires only Java 8 (or higher). Sources, documentation and the compiled library are archived as jar files into the `./target/scala-2.11/` or `./target/scala-2.12/` directory. 

## Add ScalaTIKZ executables in your default PATH

You can add the CLI tools to your default PATH, in order to directly call ScalaTIKZ tool from anywhere in the command
line interface. Depending on your OS configuration you have to add and export the path `/path/to/ScalaTIKZ-<version>/bin`
to the `PATH` variable.

For example, lets say that the ScalaTIKZ distribution version 0.3.5-SNAPSHOT is being installed in your home directory in
`$HOME/ScalaTIKZ-0.3.5`, the directory structure inside the ScalaTIKZ directory is the following:

```bash
ScalaTIKZ-0.3.5/
|-- bin
|-- etc
|-- lib
```

### Linux, Unix and MacOS X

The `bin` sub-directory contains the executable CLI. In order to add this sub-directory in your default `PATH`
add the following line in you profile file.

**BASH** e.g., inside `.profile`, `.bashrc` or `.bash_profile` file in your home directory:
```bash
export PATH=$PATH:$HOME/ScalaTIKZ-0.3.5/bin
```

## Local publish ScalaTIKZ

Follow the instructions in Section [Instructions to build ScalaTIKZ from source](#Instructions-to-build-ScalaTIKZ-from-source) and then publish locally ScalaTIKZ:

```bash
$ sbt +publishLocal
```

ScalaTIKZ cross builds for Scala versions 2.11 and 2.12. Thereafter, in order to link ScalaTIKZ (e.g., version 0.3.5-SNAPSHOT) to
your [SBT](http://www.scala-sbt.org/) project, add the following dependency:

```
libraryDependencies += "com.github.vagmcs" %% "scalatikz" % "0.3.5"
```

Similarly, in an [Apache Maven](https://maven.apache.org/) pom file:

```xml
<dependency>
    <groupId>com.github.vagmcs</groupId>
    <artifactId>scalatikz_2.11</artifactId>
    <version>0.3.5</version>
</dependency>
```
or

```xml
<dependency>
    <groupId>com.github.vagmcs</groupId>
    <artifactId>scalatikz_2.12</artifactId>
    <version>0.3.5</version>
</dependency>
```

## Usage of ScalaTIKZ through Maven Central

ScalaTIKZ is published into the Maven Central. In order to link ScalaTIKZ to your [SBT](http://www.scala-sbt.org/) project, add the following dependency:

```sbt
libraryDependencies += "com.github.vagmcs" %% "scalatikz" % "0.3.5"
```

Likewise in an [Apache Maven](https://maven.apache.org/) pom xml file add:

```xml
<dependency>
  <groupId>com.github.vagmcs</groupId>
  <artifactId>scalatikz_2.11</artifactId>
  <version>0.3.5</version>
</dependency>
```

or

```xml
<dependency>
  <groupId>com.github.vagmcs</groupId>
  <artifactId>scalatikz_2.12</artifactId>
  <version>0.3.5</version>
</dependency>
```
