# ScalaTIKZ

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vagmcs/scalatikz_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vagmcs/scalatikz_2.11)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)

ScalaTIKZ is an open-source library for publication quality [PGF/TIKZ](https://en.wikipedia.org/wiki/PGF/TikZ) vector graphics
using the [Scala programming language](http://scala-lang.org).

## License

ScalaTIKZ comes with ABSOLUTELY NO WARRANTY. This is free software, and you are welcome to redistribute it
under certain conditions; See the [GNU Lesser General Public License v3 for more details](http://www.gnu.org/licenses/lgpl-3.0.html).

## Features

1. 2D PGF plots including lines, scatters, bars, stems and many other options.
2. Chart plots (pie, clouds, squares).
3. Configure each plot/chart using a large pool of parameters.
4. Plot any number of data sequences in one figure.
5. Support for figure arrays and figures having a secondary y-axis.
6. Support for Automata plots.
7. High level API for declaring plot types and subplots.
8. Save generated graphics as TeX, PDF, JPEG or PNG formats.
9. Command line interface that can be used as a Unix/Linux shell script.

## Requirements

ScalaTIKZ requires to have a TeXLive distribution installed on your system. Moreover, in case you
want to export your plots as image formats, GhostScript package is also required.

For more details please see [Requirements](docs/requirements.md)

## How to get ScalaTIKZ

ScalaTIKZ is published to Maven Central for Scala 2.11, 2.12 and 2.13. 

Add the following dependency to your SBT build file in order to get started:

```sbt
libraryDependencies ++= "com.github.vagmcs" %% "scalatikz" % "0.4.2",
```

## Documentation

- [Building and Linking](docs/building_and_linking.md)
- [PGFPlot Gallery](docs/pgfplots_examples.md)
- [Chart Gallery](docs/charts_examples.md)
- [Automata Gallery](docs/automata_examples.md)
- [Documentation](docs/index.md)

## Contributions

Contributions are welcome, for details see [CONTRIBUTING.md](CONTRIBUTING.md).