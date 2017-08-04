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

package scalatikz.app

import java.io.File
import scala.util.{Failure, Success}
import scalatikz.app.GraphType._
import scalatikz.common.CSV
import scalatikz.graphics.pgf.Color._
import scalatikz.graphics.pgf.DataTypes._
import scalatikz.graphics.pgf.FontSize._
import scalatikz.graphics.pgf.LineSize._
import scalatikz.graphics.pgf.LineStyle._
import scalatikz.graphics.pgf.Mark._
import scalatikz.graphics.pgf.LegendPos._
import scalatikz.graphics.pgf._

object ScalaTIKZ extends AppCLI[Conf]("scalatikz") {

  // An iterator over available colors in case user does not specify one.
  private val colorIterator = Iterator.continually(colors).flatten
  private var currentColor: Color = colors.head

  private def nextColor: Color = {
    currentColor = colorIterator.next
    currentColor
  }

  // General options

  note("General options:\n".underlined.cyan.bold)

  opt[String]('n', "name".underlined).valueName("<string>".bold).optional.unbounded
    .action { (name, conf) =>
        if (conf.figure.name != DEFAULT_NAME) fatal(s"Name is already defined to '${conf.figure.name}'")
        else conf.copy(figure = conf.figure.havingName(name))
    }.text("Specify a name for the figure (default is 'result').\n")

  opt[String]('o', "output".underlined).valueName("<output path>".bold).optional.unbounded
    .action { (path, conf) =>
      if (conf.output != System.getProperty("user.dir")) fatal(s"Output path is already defined to '${conf.output}'")
      else conf.copy(output = path)
    }.validate { path =>
      if (new File(path).isDirectory) success
      else failure(s"Path '$path' is not a valid directory.")
    }.text("Specify a path for the output directory (default is the current directory).\n")

  opt[String]('F', "format".underlined).valueName("<PDF|PNG|JPEG|TEX>".bold)
    .action( (format, conf) => conf.copy(format = format) )
    .text("Output format (default is PDF).")
    .validate {
      case "PDF" | "PNG" | "JPEG" | "TEX" => success
      case _ => failure("Format should be PDF or PNG or JPEG or TEX.")
    }

  // Plot types

  note("\nPlot types:\n".underlined.cyan.bold)

  opt[Unit]('P', "plot".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(PLOT)) )
    .text("Plots a 2D line of the data in Y versus the corresponding values in X.")

  opt[Unit]('M', "stem".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(STEM)) )
    .text("Plots a data sequence as stems emerging from a baseline along the x-axis.")

  opt[Unit]('A', "area".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(AREA)) )
    .text("Plots a data sequence as a 2D line and fills the area beneath the curve.")

  opt[Unit]('C', "scatter".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(SCATTER)) )
    .text("Plots a scatter of points at the locations specified by the data sequence.")

  opt[Unit]('T', "stair".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(STAIR)) )
    .text("Plots a data sequence as a 2D stair step.")

  opt[Unit]('R', "error-bar".underlined).unbounded
    .action( (_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(ERROR_BAR)) )
    .text("Plots a data sequence as a 2D line along vertical and/or horizontal error bars at each data point.")

  // Input data options

  note("\nInput data options:\n".underlined.cyan.bold)

  opt[String]('i', "input".underlined).valueName("<csv file>".bold).required.unbounded
    .action( (f, conf) => conf.copy(inputs = conf.inputs :+ f) )
    .text("Specify an input CSV data file (required).\n")
    .validate { csv =>
      if (new File(csv).isFile) success
      else failure(s"File '$csv' does not exists.")
    }

  opt[Char]('d', "del".underlined).valueName("<character>".bold).optional.unbounded
    .action( (d, conf) => conf.copy(delimiters = conf.delimiters :+ d) )
    .text("Specify the delimiter of the input csv data file (default is comma)." +
          "\n\tNote: ".green.bold + "In case you need to specify TAB as a delimiter, please just type $'\\t'\n")

  opt[String]('x', "x-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (xColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xColumn = Some(xColumn)))
    }.text("X column name or column index (default is the indexes of the y values)." +
          s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('y', "y-column".underlined).valueName("<index or name>".bold).required.unbounded
    .action { (yColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(yColumn = Some(yColumn)))
    }.text("Y column name or column index (required)." +
          s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('e', "x-error-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (xErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xErrorColumn = Some(xErrorColumn)))
    }.text("X error column index or column name (optional)." +
          s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('E', "y-error-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (yErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xErrorColumn = Some(yErrorColumn)))
    }.text("Y error column index or column name (optional)." +
          s"\n\t${"Note:".green.bold} Indexes start from 0.")

  // Plot options

  note("\nPlot options:".underlined.cyan.bold)
  note("CAUTION: ".red + "These options should appear only after a plot type option.\n")

  opt[Color]('c', "color".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineColor = Some(color)))
    }.text(s"Line color. Available line colors: ${colors.mkString(", ")}" +
          "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
          s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Mark]('m', "marker".underlined).valueName("<mark>".bold).unbounded.optional
    .action { (mark, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a marker option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(marker = Some(mark)))
    }.text(s"Point marker. Available markers: ${markers.tail.mkString(", ")}\n")

  opt[Color]('k', "mark-stroke".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark stroke color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markStrokeColor = Some(color)))
    }.text(s"Mark stroke color. Available line colors: ${colors.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Color]('f', "mark-fill".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark fill color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markFillColor = Some(color)))
    }.text(s"Mark fill color. Available line colors: ${colors.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Double]('s', "mark-size".underlined).valueName("<double>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markSize = size))
    }.text("Marker size (default is 2).\n")

  opt[LineSize]('S', "line-size".underlined).valueName("<size>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineSize = Some(size)))
    }.text(s"Set line size (default is thin)." +
          s"\n\t${"Available line sizes:".green.bold} ${lineSizes.mkString(", ")}\n")

  opt[LineStyle]('b', "line-style".underlined).valueName("<style>".bold).unbounded.optional
    .action { (style, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line style option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineStyle = Some(style)))
    }.text(s"Set line style (default is solid)." +
          s"\n\t${"Available line styles:".green.bold} ${lineStyles.mkString(", ")}\b")

  opt[Unit]('h', "smooth".underlined).unbounded.optional
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a smooth option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(smooth = true))
    }.text("Smooth lines.\n")

  opt[Double]('O', "opacity".underlined).valueName("<double>".bold).unbounded.optional
    .action { (opacity, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before an opacity option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(opacity = opacity))
    }.text("Set the opacity of the area under a curve.\n")

  opt[Unit]('r', "constant-area".underlined).unbounded.optional
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a constant area option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(constant = true))
    }.text("Enable constant area plots.")

  // Figure options

  note("\nFigure options:\n".underlined.cyan.bold)

  opt[String]('t', "title".underlined).valueName("<string>".bold).optional.unbounded
    .action { (title, conf) =>
      conf.copy(figure = conf.figure.havingTitle(title))
    }.text("Specify a title for the figure (optional).\n")

  opt[String]('X', "X-label".underlined).valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingXLabel(label))
    }.text("Specify a X label (optional).\n")

  opt[String]('Y', "Y-label".underlined).valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingYLabel(label))
    }.text("Specify a Y label (optional).\n")

  opt[Unit]('J', "majorGridOn".underlined).optional.unbounded
    .action( (_, conf) => conf.copy(figure = conf.figure.havingMajorGridOn) )
    .text("Enables major grid (optional).\n")

  opt[Unit]('N', "minorGridOn").optional.unbounded
    .action( (_, conf) => conf.copy(figure = conf.figure.havingMinorGridOn) )
    .text("Enables minor grid (optional).\n")

  opt[Unit]('B', "bothGridOn".underlined).optional.unbounded
    .action( (_, conf) => conf.copy(figure = conf.figure.havingGridsOn) )
    .text("Enables both minor and major grids (optional).\n")

  opt[FontSize]('u', "font-size".underlined).valueName("<size>".bold).optional.unbounded
    .action { (size, conf) =>
      conf.copy(figure = conf.figure.havingFontSize(size))
    }.text(s"Set figure's font size (default is $NORMAL). " +
          s"\n\t${"Available font sizes:".green.bold} ${fontSizes.mkString(", ")}\n")

  opt[Seq[Double]]('a', "axis".underlined).valueName("<double,double,double,double>".bold).optional.unbounded
    .action( (limits, conf) => conf.copy(figure = conf.figure.havingLimits(limits.head, limits(1), limits(2), limits(3))) )
    .text("Set X and Y axis limits as comma separated values: x minimum,x maximum,y minimum,y maximum.\n")
    .validate { seq =>
      if (seq.length == 4) success
      else failure("Axis limits should be exactly 4.")
    }

  opt[Seq[String]]('g', "legends".underlined).valueName("<comma separated legends>".bold).optional.unbounded
    .action( (x, conf) => conf.copy(figure = conf.figure.havingLegends(x:_*)) )
    .text("Comma separated legends for the plotted data.\n")

  opt[LegendPos]('p', "legend-pos".underlined).valueName("<position>".bold).optional.unbounded
    .action( (x, conf) => conf.copy(figure = conf.figure.havingLegendPos(x)) )
    .text(s"Change legend panel position (default is outer north east)." +
          s"\n\t${"Available legend positions:".green.bold} ${legendsPositions.mkString(", ")}\n")

  opt[Unit]('l', "logX".underlined).optional.unbounded
    .action( (_, conf) => conf.copy(figure = conf.figure.havingLogXAxis) )
    .text("Enables logarithmic X scale (default is linear).\n")

  opt[Unit]('L', "logY".underlined).optional.unbounded
    .action( (_, conf) => conf.copy(figure = conf.figure.havingLogYAxis) )
    .text("Enables logarithmic Y scale (default is linear).\n")

  help("help").text("Print usage options.\n")

  version("version").text("Display the version.")

  /*
   * Parse arguments
   */
  parse(args, Conf()) match {
    case Some(conf) if conf.graphics.nonEmpty =>

      var resultedFigure = conf.figure

      def notFound[T](seq: IndexedSeq[T])(index: Int): String = {
        logger.info(s"CSV not found for plot type ${index + 1}. Using '${conf.inputs.last}'.")
        conf.inputs.last
      }

      conf.graphics.zipWithIndex.foreach { case (graphic, i) =>

        val coordinates: Coordinates =
          (CSV.parseColumns(
            new File(conf.inputs.applyOrElse(i, notFound(conf.inputs))),
            conf.delimiters.applyOrElse(i, (_: Int) => ','),
            Seq(graphic.xColumn, graphic.yColumn).flatten: _*): @unchecked) match {

            case Success(y :: Nil) => (1 to y.length) zip y
            case Success(x :: y :: Nil) => x zip y
            case Failure(ex) => fatal(s"${ex.getMessage}")
          }

        graphic.graph match {
          case PLOT =>

            resultedFigure =
              resultedFigure.plot(
                color = graphic.lineColor.getOrElse(nextColor),
                marker = graphic.marker.getOrElse(NONE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize,
                lineStyle = graphic.lineStyle.getOrElse(SOLID),
                lineSize = graphic.lineSize.getOrElse(THIN),
                smooth = graphic.smooth
              )(coordinates)

          case STEM =>

            resultedFigure =
              resultedFigure.stem(
                color = graphic.lineColor.getOrElse(nextColor),
                marker = graphic.marker.getOrElse(NONE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize
              )(coordinates)

          case SCATTER =>

            resultedFigure =
              resultedFigure.scatter(
                marker = graphic.marker.getOrElse(CIRCLE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize
              )(coordinates)

          case AREA =>

            resultedFigure =
              resultedFigure.area(
                color = graphic.lineColor.getOrElse(nextColor),
                marker = graphic.marker.getOrElse(NONE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize,
                lineStyle = graphic.lineStyle.getOrElse(SOLID),
                lineSize = graphic.lineSize.getOrElse(THIN),
                opacity = graphic.opacity,
                smooth = graphic.smooth,
                constant = graphic.constant
              )(coordinates)

          case STAIR =>

            resultedFigure =
              resultedFigure.stair(
                color = graphic.lineColor.getOrElse(nextColor),
                marker = graphic.marker.getOrElse(NONE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize,
                lineStyle = graphic.lineStyle.getOrElse(SOLID),
                lineSize = graphic.lineSize.getOrElse(THIN)
              )(coordinates)

          case ERROR_BAR =>

            val error: Coordinates =
              (CSV.parseColumns(
                new File(conf.inputs.applyOrElse(i, notFound(conf.inputs))),
                conf.delimiters.applyOrElse(i, (_: Int) => ','),
                Seq(graphic.xErrorColumn, graphic.yErrorColumn).flatten: _*): @unchecked) match {

                case Success(Nil) =>
                  val zeroError = Seq.fill(coordinates.length)(0.0)
                  zeroError zip zeroError

                case Success(y :: Nil) if graphic.xErrorColumn.isEmpty =>
                  Seq.fill(coordinates.length)(0.0) zip y

                case Success(x :: Nil) if graphic.yErrorColumn.isEmpty =>
                  x zip Seq.fill(coordinates.length)(0.0)

                case Success(x :: y :: Nil) => x zip y
                case Failure(ex) => fatal(s"${ex.getMessage}")
              }

            resultedFigure =
              resultedFigure.errorBar(
                color = graphic.lineColor.getOrElse(nextColor),
                marker = graphic.marker.getOrElse(NONE),
                markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markFillColor = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                markSize = graphic.markSize,
                lineStyle = graphic.lineStyle.getOrElse(SOLID),
                lineSize = graphic.lineSize.getOrElse(THIN),
                smooth = graphic.smooth
              )(coordinates)(error)
        }
      }

      // Try to save the resulted figure in the requested FORMAT.
      val result = conf.format.toUpperCase match {
        case "PDF" => resultedFigure.saveAsPDF(conf.output)
        case "PNG" => resultedFigure.saveAsPNG(conf.output)
        case "JPEG" => resultedFigure.saveAsJPEG(conf.output)
        case "TEX" => resultedFigure.saveAsTeX(conf.output)
      }
      result match {
        case Success(f) =>
          logger.info(s"${conf.format} successfully saved in $f.")
        case Failure(ex) =>
          fatal(s"${conf.format} cannot be saved: ${ex.getMessage}")
      }

    case Some(conf) if conf.graphics.isEmpty =>
      reportError("Please specify a plot type.")
      showTryHelp()

    case None => // do nothing, option parser should handle the case
  }
}