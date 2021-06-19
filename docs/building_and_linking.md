# ScalaTIKZ on the terminal

First, you need to build ScalaTIKZ from source. I assure you that it is not that difficult, you just need to have Java 8 or higher and [SBT](http://www.scala-sbt.org/) installed in your system.
 
## Build ScalaTIKZ from source

To build the ScalaTIKZ distribution, navigate into the root directory and simply type the following command:

```
$ sbt dist
```

After a successful compilation, the distribution is located inside the `target/universal/ScalaTIKZ-*.zip` archive. You can extract the archive and add the `path/to/ScalaTIKZ-<version>/bin` in your `PATH`, in order to execute ScalaTIKZ from the terminal.

## Add ScalaTIKZ in your PATH

Depending on your OS configuration you have to add and export the `path/to/ScalaTIKZ-<version>/bin` to the `PATH` environment variable.

For instance, lets say that the ScalaTIKZ distribution version `0.5.0` is being installed in your home directory in `${HOME}/ScalaTIKZ-0.5.0`, thus, the directory structure inside the ScalaTIKZ directory should be as follows:

```bash
ScalaTIKZ-0.5.0/
|-- bin
|-- etc
|-- lib
```

### Linux, Unix and macOS

The `bin` subdirectory contains the ScalaTIKZ executables. In order to add this subdirectory in your default `PATH` add the following line in your shell profile.

```bash
export PATH="${PATH}:${HOME}/ScalaTIKZ-0.5.0/bin"
```
