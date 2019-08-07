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

package scalatikz.pgf.charts

import scalatikz.pgf.TIKZPicture
import scalatikz.pgf.charts.enums.TextLocation
import scalatikz.pgf.enums.Color
import Numeric.Implicits._

case class Chart private(
  override val name: String,
  private[charts] val conf: ChartConf,
  private[charts] val variation: Option[String],
  data: Map[String, Double]) extends TIKZPicture {

  override protected val libraries: String = """\usepackage{pgf-pie}"""

  /**
    * Rename the chart.
    *
    * @param name a name for the chart
    * @return a Chart having the given name
    */
  def havingName(name: String): Chart = copy(name = name)

  /**
    * Magnify chart pieces according to their importance.
    *
    * @return a Chart having magnified pieces
    */
  def magnify: Chart = copy(conf = conf.copy(magnify = true))

  /**
    * Scale the chart by the given radius.
    *
    * @param radius the radius of the chart
    * @return a Chart having the given radius
    */
  def havingRadius(radius: Int): Chart = copy(conf = conf.copy(radius = radius))

  /**
    * Rotate the chart by the given degrees.
    *
    * @param degrees the rotation degrees
    * @return a Chart rotated by the given degrees
    */
  def rotate(degrees: Int): Chart = copy(conf = conf.copy(rotationDegrees = degrees))

  /**
    * Detach pieces of the chart according to the given factor.
    *
    * @param factor the explode factor
    * @return a Chart having the given explode factor
    */
  def havingExplodeFactor(factor: Double): Chart = copy(conf = conf.copy(explodeFactor = factor))

  /**
    * Set the location of the text labels.
    *
    * @param textLocation the text location of labels
    * @return a Chart having the given text location
    */
  def havingText(textLocation: TextLocation): Chart = copy(conf = conf.copy(textLocation = textLocation))

  /**
    * Set pieces colors.
    *
    * @param colors a sequence of colors
    * @return a Chart having the given colors
    */
  def havingColors(colors: Color*): Chart = copy(conf = conf.copy(colors = Some(colors.toSeq)))

  /**
    * @param text the text to appear before numbers
    * @return a Chart having the given text before numbers
    */
  def havingTextBeforeNumber(text: String): Chart = copy(conf = conf.copy(textBeforeNumbers = Some(text)))

  /**
    * @param text the text to appear after numbers
    * @return a Chart having the given text after numbers
    */
  def havingTextAfterNumber(text: String): Chart = copy(conf = conf.copy(textAfterNumbers = Some(text)))

  override def toString: String = {
    raw"""
       |\pie[${if (variation.isEmpty) "" else s"${variation.get},"}
       |  sum=auto,
       |  $conf
       |] {${data.map { case (label, percentage) => s"${"%1.2f".format(percentage)}/$label" }.mkString(",")}}
    """.stripMargin
  }
}

object Chart {

  private def apply[V: Numeric](name: String, variation: Option[String], data: Map[String, V]): Chart =
    Chart(name, ChartConf(), variation, data.map { case (k, v) => k -> v.toDouble })

  private def apply[T](name: String, variation: Option[String], data: Seq[T]): Chart = {
    val len = data.length.toDouble
    val map = data.map(_ -> 1).groupBy(_._1).toSeq.map {
      case (key, seq) => key.toString -> seq.foldLeft(0.0)(_ + _._2) / len
    }.toMap

    Chart(name, variation, map)
  }

  def pie[V: Numeric](name: String, data: Map[String, V]): Chart = Chart(name, None, data)

  def pie[T](name: String)(data: T*): Chart = Chart(name, None, data.toSeq)

  def square[V: Numeric](name: String, data: Map[String, V]): Chart = Chart(name, Some("square"), data)

  def square[T](name: String, data: T*): Chart = Chart(name, Some("square"), data.toSeq)

  def cloud[V: Numeric](name: String, data: Map[String, V]): Chart = Chart(name, Some("cloud"), data)

  def cloud[T](name: String)(data: T*): Chart = Chart(name, Some("cloud"), data.toSeq)
}
