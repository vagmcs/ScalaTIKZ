/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A PGF/TIKZ plot library for Scala.
 *     
 */

package scalatikz.pgf.plots.types

import scalatikz.pgf.enums.{ LineSize, LineStyle }
import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.Mark

/**
 * Creates a 2D mesh line of the data in Y versus the corresponding values in X.
 *
 * @param coordinates sequence of x, y points in the Euclidean space
 * @param lineStyle line style
 * @param lineSize line size
 */
case class Mesh(
  coordinates: Coordinates2D,
  lineStyle: LineStyle,
  lineSize: LineSize)
    extends PGFPlot {

  override def toString: String = raw"""
      |\addplot[
      |  mesh,
      |  $lineStyle,
      |  $lineSize
      |] coordinates {
      |${coordinates.mkString("\n")}
      |};
    """.stripMargin
}

/**
 * Creates a scatter mesh of data points.
 *
 * @note Scatter plot is also called a bubble plot.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param marker mark style
 * @param markSize mark size
 */
case class MeshScatter(
  coordinates: Coordinates2D,
  marker: Mark,
  markSize: Double)
    extends PGFPlot {

  override def toString: String = raw"""
      |\addplot[
      |  scatter,
      |  only marks,
      |  mark=$marker,
      |  mark size=${markSize}pt,
      |] coordinates {
      |${coordinates.mkString("\n")}
      |};
    """.stripMargin
}
