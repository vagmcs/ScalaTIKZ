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

import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.{ Color, ErrorDirection, Mark }

case class Error(
    data: Coordinates2D,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    xDirection: ErrorDirection,
    yDirection: ErrorDirection) {

  override def toString: String = {
    s"""
      |error bars/.cd,
      |error bar style=solid,
      |error mark=$marker,
      |error mark options={draw=$markStrokeColor, fill=$markFillColor, mark size=${markSize}pt},
      |x dir=$xDirection, x explicit
      |y dir=$yDirection, y explicit
    """.stripMargin
  }
}
