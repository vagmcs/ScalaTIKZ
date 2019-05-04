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

package scalatikz.graphics.pgf.plots

import scalatikz.graphics.PGFPlot
import scalatikz.graphics.pgf.DataTypes.Coordinates
import scalatikz.graphics.pgf.enums.{ LineSize, LineStyle }

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param lineStyle line style
  * @param lineSize line size
  */
case class Mesh(
    coordinates: Coordinates,
    lineStyle: LineStyle,
    lineSize: LineSize) extends PGFPlot {

  override def toString: String =
    raw"""
         |\addplot[
         |  mesh,
         |  $lineStyle,
         |  $lineSize
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
}
