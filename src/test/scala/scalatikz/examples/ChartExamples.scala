package scalatikz.examples

import scalatikz.pgf.charts.Chart
import scalatikz.pgf.charts.enums.TextLocation
import scalatikz.pgf.enums.Color
import scalatikz.pgf.enums.Color.BLACK
import scala.util.Random

object ChartExamples extends App {

  Chart.pie("simple_pie", "A" -> 10, "B" -> 20, "C" -> 30, "D" -> 40)
    .havingRadius(4)
    .rotate(180)
    .saveAsPNG("images/charts")

  Chart.pie("color_wheel")(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    .havingTextAfterNumber("\\%")
    .saveAsPNG("images/charts")

  Chart.pie("grayscale_pie", "A" -> 10, "B" -> 20, "C" -> 30, "D" -> 40)
    .havingColors(BLACK!10, BLACK!20, BLACK!30, BLACK!40)
    .havingExplodeFactor(0.1)
    .saveAsPNG("images/charts")

  Chart.pie("legends_pie", Map("First" -> 10, "Second" -> 20, "Third" -> 30, "Forth" -> 40))
    .havingText(TextLocation.LEGEND)
    .magnify
    .saveAsPNG("images/charts")

  val randomBitString = (1 to 100) map (_ => Random.nextInt(2))
  Chart.cloud("bit_frequency")(randomBitString)
    .havingText(TextLocation.PIN)
    .havingColors(Color.GRAY, Color.LIGHT_GRAY)
    .magnify
    .saveAsPNG("images/charts")
}
