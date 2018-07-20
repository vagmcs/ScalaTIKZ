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

package scalatikz.graphics

/**
  * Graphic is any construct that can be placed inside a
  * TIKZ picture environment.
  */
sealed trait Graphic

/**
  * PGFPlot is any plot construct that is supported by
  * the PGF library.
  */
trait PGFPlot extends Graphic
