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
import scalatikz.common.CSV
import scalatikz.graphics.pgf.Color._
import scalatikz.graphics.pgf.DataTypes._
import scalatikz.graphics.pgf.FontSize._
import scalatikz.graphics.pgf.LineSize._
import scalatikz.graphics.pgf.LineStyle._
import scalatikz.graphics.pgf.Mark._
import scalatikz.graphics.pgf.LegendPos._
import scalatikz.graphics.pgf._


final case class Conf(output: String = System.getProperty("user.dir"),
                      format: String = "PDF",
                      figure: Figure = Figure("result"),
                      graphics: Seq[GraphicConf] = Seq.empty)

final case class GraphicConf(plotType: String,
                             filename: Option[String] = None,
                             delimiter: Char = ',',
                             xColumn: Option[String] = None,
                             yColumn: Option[String] = None,
                             xErrorColumn: Option[String] = None,
                             yErrorColumn: Option[String] = None,
                             lineColor: Option[Color] = None,
                             marker: Option[Mark] = None,
                             markStrokeColor: Option[Color] = None,
                             markFillColor: Option[Color] = None,
                             markSize: Double = 2,
                             lineStyle: Option[LineStyle] = None,
                             lineSize: Option[LineSize] = None,
                             opacity: Double = 0.5,
                             smooth: Boolean = false,
                             constant: Boolean = false)

/**
  *
  */
object ScalaTIKZ extends AppCLI[Conf]("scalatikz") {

  // An iterator over available colors in case user does not specify one.
  private val colorIterator = Iterator.continually(colors).flatten
  private var currentColor: Color = colors.head

  private def nextColor: Color = {
    currentColor = colorIterator.next
    currentColor
  }

  // TODO latex labels need \$ for math mode
  // TODO underscore needs \\_ (check title, labels and legends and replace)...

  opt[String]('n', Console.RED + "name" + Console.BLINK).valueName("<string>").optional
    .action( (name, c) => c.copy(figure = c.figure.havingName(name)) )
    .text("Specify a name for the figure (default is 'result').")

  opt[String]('o', "output").valueName("<output path>").optional
    .action( (path, c) => c.copy(output = path) )
    .text("Specify a path for the output file (default is the current directory).")
    .validate { path =>
      if (new File(path).isDirectory) success
      else failure(s"Path '$path' is not a valid directory.")
    }

  opt[String]('F', "format").valueName("<PDF|PNG|JPEG|TEX>")
    .action( (format, c) => c.copy(format = format) )
    .text("Output format (default is PDF).")
    .validate {
      case "PDF" | "PNG" | "JPEG" | "TEX" => success
      case _ => failure("Format should be PDF or PNG or JPEG or TEX.")
    }

  // TODO maybe make them arg
  arg[Unit]("plot").unbounded().optional()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("PLOT")) )
    .text("Plots a 2D line of the data in Y versus the corresponding values in X.")

  opt[Unit]('M', "stem").unbounded()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("STEM")) )
    .text("Plots a data sequence as stems that extend from a baseline along the x-axis.")

  opt[Unit]('A', "area").unbounded()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("AREA")) )
    .text("Plots a data sequence as a 2D line and fills the area beneath the curve.")

  opt[Unit]('C', "scatter").unbounded()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("SCATTER")) )
    .text("Plots a scatter of points at the locations specified by the data sequence.")

  opt[Unit]('T', "stair").unbounded()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("STAIR")) )
    .text("Plots a data sequence as a 2D stair step.")

  opt[Unit]('R', "error-bar").unbounded()
    .action( (_, c) => c.copy(graphics = c.graphics :+ GraphicConf("ERROR_BAR")) )
    .text("Plots a data sequence as a 2D line along vertical and/or horizontal error bars at each data point.")

  opt[String]('i', "input").valueName("<csv file>").required.unbounded
    .action( (f, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(filename = Some(f))) )
    .text("Specify an input csv data file.")

  opt[Char]('d', "delimiter").valueName("<character>").optional.unbounded
    .action( (d, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(delimiter = d)) )
    .text("Specify the delimiter of the input csv data file (default is comma). " +
          "In case you need to specify the TAB as a delimiter, just type $'\\t'")

  opt[String]('x', "x-column").valueName("<column index or column name>").optional.unbounded
    .action( (xColumn, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(xColumn = Some(xColumn))) )
    .text("X column name or column index (default is the natural numbers).")

  opt[String]('y', "y-column").valueName("<column index or column name>").required.unbounded
    .action( (yColumn, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(yColumn = Some(yColumn))) )
    .text("Y column name or column index.")

  opt[String]('e', "x-error-column").valueName("<column index or column name>").optional.unbounded
    .action( (xErrorColumn, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(xErrorColumn = Some(xErrorColumn))) )
    .text("X error column index or column name.")

  opt[String]('E', "y-error-column").valueName("<column index or column name>").optional.unbounded
    .action( (yErrorColumn, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(yErrorColumn = Some(yErrorColumn))) )
    .text("Y error column index or column name.")

  opt[Color]('c', "color").valueName("<color>").unbounded.optional
    .action( (color, c) => c.copy(graphics  = c.graphics.init :+ c.graphics.last.copy(lineColor = Some(color))) )
    .text(s"Line color. Available line colors: ${colors.mkString(", ")}" +
          s"\n\t\tNOTE: Colors can also be mixed by using the symbol #: " +
          s"\n\t\tFor instance red#20#green defines a color that has 20% red and 80% green.")

  opt[Mark]('m', "marker").valueName("<mark>").unbounded.optional
    .action( (mark, c) => c.copy(graphics  = c.graphics.init :+ c.graphics.last.copy(marker = Some(mark))) )
    .text(s"${Console.BLUE} Available markers: ${markers.tail.mkString(", ")} ${Console.BLINK}")

  opt[Color]('k', "mark-stroke").valueName("<color>").unbounded.optional
    .action( (color, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(markStrokeColor = Some(color))) )
    .text(s"Mark stroke color. Available colors: ${colors.mkString(", ")}" +
      s"\n\t\tNOTE: Colors can also be mixed by using the symbol #: " +
      s"\n\t\tFor instance red#20#green defines a color that has 20% red and 80% green.")

  opt[Color]('f', "mark-fill").valueName("<color>").unbounded.optional
    .action( (color, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(markFillColor = Some(color))) )
    .text(s"Mark fill color. Available colors: ${colors.mkString(", ")}" +
      s"\n\t\tNOTE: Colors can also be mixed by using the symbol #: " +
      s"\n\t\tFor instance red#20#green defines a color that has 20% red and 80% green.")

  opt[Double]('s', "mark-size").valueName("<double>").unbounded.optional
    .action( (size, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(markSize = size)) )
    .text("Marker size (default is 2).")

  opt[LineSize]('S', "line-size").valueName("<size>").unbounded.optional
    .action( (size, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(lineSize = Some(size))) )
    .text(s"Available line sizes: ${lineSizes.mkString(", ")}")

  opt[LineStyle]('b', "line-style").valueName("<style>").unbounded.optional
    .action( (style, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(lineStyle = Some(style))) )
    .text(s"Available line styles: ${lineStyles.mkString(", ")}")

  opt[Unit]('h', "smooth").unbounded.optional
    .action( (_, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(smooth = true)) )
    .text("Smooth line plot.")

  opt[Double]('O', "opacity").valueName("<double>").unbounded.optional
    .action( (opacity, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(opacity = opacity)) )
    .text("Set the opacity of the area under a curve.")

  opt[Unit]('r', "constant-stair").unbounded.optional
    .action( (_, c) => c.copy(graphics = c.graphics.init :+ c.graphics.last.copy(constant = true)) )
    .text("Enable constant area plots.")

  opt[String]('X', "X-label").valueName("<string>").optional.maxOccurs(1)
    .action( (label, c) => c.copy(figure = c.figure.havingXLabel(label)) )
    .text("X label.")

  opt[String]('Y', "Y-label").valueName("<string>").optional.maxOccurs(1)
    .action( (label, c) => c.copy(figure = c.figure.havingYLabel(label)) )
    .text("Y label.")

  opt[Unit]('J', "majorGridOn").optional.maxOccurs(1)
    .action( (_, c) => c.copy(figure = c.figure.havingMajorGridOn) )
    .text("Enables major grid.")

  opt[Unit]('N', "minorGridOn").optional.maxOccurs(1)
    .action( (_, c) => c.copy(figure = c.figure.havingMinorGridOn) )
    .text("Enables minor grid.")

  opt[Unit]('B', "bothGridOn").optional.maxOccurs(1)
    .action( (_, c) => c.copy(figure = c.figure.havingGridsOn) )
    .text("Enables both minor and major grids.")

  opt[String]('t', "title").valueName("<string>").optional.maxOccurs(1)
    .action( (title, c) => c.copy(figure = c.figure.havingTitle(title)) )
    .text("Set the title of the figure.")

  opt[FontSize]('u', "font-size").valueName("<double>").optional.maxOccurs(1)
    .action( (size, c) => c.copy(figure = c.figure.havingFontSize(size)) )
    .text(s"Set figure's font size. Available font sizes: ${fontSizes.mkString(", ")}")

  opt[Seq[Double]]('a', "axis").valueName("<double,double,double,double>").optional.maxOccurs(1)
    .action( (limits, c) => c.copy(figure = c.figure.havingLimits(limits.head, limits(1), limits(2), limits.last)) )
    .text("Set X and Y axis limits as comma separated values: xMin,xMax,yMin,yMax.")
    .validate { seq =>
      if (seq.length == 4) success
      else failure("Axis limits should be exactly 4.") // TODO: does not work with ints
    }

  opt[Seq[String]]('g', "legends").valueName("<comma separated legends>").optional.maxOccurs(1)
    .action( (x, c) => c.copy(figure = c.figure.havingLegends(x:_*)) )
    .text("Comma separated legends for the plotted data.")

  opt[LegendPos]('p', "legend-pos").valueName("<position>").optional.maxOccurs(1)
    .action( (x, c) => c.copy(figure = c.figure.havingLegendPos(x)) )
    .text(s"Available legend positions: ${legendsPositions.mkString(", ")}")

  opt[Unit]('l', "logX").optional.maxOccurs(1)
    .action( (_, c) => c.copy(figure = c.figure.havingLogXAxis) )
    .text("Enables logarithmic X scale (default is linear).")

  opt[Unit]('L', "logY").optional.maxOccurs(1)
    .action( (_, c) => c.copy(figure = c.figure.havingLogYAxis) )
    .text("Enables logarithmic Y scale (default is linear).")

  println(args.deep)

  parse(args, Conf()) match {
    case Some(c) =>

      var resultedFigure = c.figure

      c.graphics.foreach { g =>

        val coordinates: Coordinates =
          (CSV.parseColumns(
            new File(g.filename.get),
            g.delimiter,
            Seq(g.xColumn, g.yColumn).flatten: _*): @unchecked) match {

            case Success(y :: Nil) => (1 to y.length) zip y
            case Success(x :: y :: Nil) => x zip y
            case Failure(ex) =>
              logger.error(s"${ex.getMessage}")
              Seq.empty
          }

        if (coordinates.isEmpty) System.exit(1)

        g.plotType match {
          case "PLOT" =>

            resultedFigure =
              resultedFigure.plot(
                color = g.lineColor.getOrElse(nextColor),
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize,
                lineStyle = g.lineStyle.getOrElse(SOLID),
                lineSize = g.lineSize.getOrElse(THIN),
                smooth = g.smooth
              )(coordinates)

          case "STEM" =>

            resultedFigure =
              resultedFigure.stem(
                color = g.lineColor.getOrElse(nextColor),
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize
              )(coordinates)

          case "SCATTER" =>

            resultedFigure =
              resultedFigure.scatter(
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize
              )(coordinates)

          case "AREA" =>

            resultedFigure =
              resultedFigure.area(
                color = g.lineColor.getOrElse(nextColor),
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize,
                lineStyle = g.lineStyle.getOrElse(SOLID),
                lineSize = g.lineSize.getOrElse(THIN),
                opacity = g.opacity,
                smooth = g.smooth,
                constant = g.constant
              )(coordinates)

          case "STAIR" =>

            resultedFigure =
              resultedFigure.stair(
                color = g.lineColor.getOrElse(nextColor),
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize,
                lineStyle = g.lineStyle.getOrElse(SOLID),
                lineSize = g.lineSize.getOrElse(THIN)
              )(coordinates)

          case "ERROR_BAR" =>

            val error: Coordinates =
              (CSV.parseColumns(
                new File(g.filename.get),
                g.delimiter,
                Seq(g.xErrorColumn, g.yErrorColumn).flatten: _*): @unchecked) match {

                case Success(Nil) =>
                  val zeroError = (1 to coordinates.length).map(_ => 0)
                  zeroError zip zeroError
                case Success(y :: Nil) => (1 to y.length).map(_ => 0) zip y
                case Success(x :: y :: Nil) => x zip y
                case Failure(ex) =>
                  logger.error(s"${ex.getMessage}")
                  Seq.empty
              }

            if (error.isEmpty) System.exit(1)

            resultedFigure =
              resultedFigure.errorBar(
                color = g.lineColor.getOrElse(nextColor),
                marker = g.marker.getOrElse(NONE),
                markStrokeColor = g.markStrokeColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markFillColor = g.markFillColor.getOrElse(g.lineColor.getOrElse(currentColor)),
                markSize = g.markSize,
                lineStyle = g.lineStyle.getOrElse(SOLID),
                lineSize = g.lineSize.getOrElse(THIN),
                smooth = g.smooth
              )(coordinates)(error)
        }
      }

      // Try to save the resulted figure in the requested FORMAT.
      val result = c.format.toUpperCase match {
        case "PDF" => resultedFigure.saveAsPDF(c.output)
        case "PNG" => resultedFigure.saveAsPNG(c.output)
        case "JPEG" => resultedFigure.saveAsJPEG(c.output)
        case "TEX" => resultedFigure.saveAsTeX(c.output)
      }
      result match {
        case Success(f) =>
          logger.info(s"${c.format} successfully saved in $f.")
        case Failure(ex) =>
          logger.error(s"${c.format} cannot be saved: ${ex.getMessage}")
          System.exit(1)
      }

    case None => // do nothing
  }
}
