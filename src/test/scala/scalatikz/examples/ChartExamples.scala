package scalatikz.examples

import scalatikz.pgf.charts.Chart
import scalatikz.pgf.charts.enums.TextLocation
import scalatikz.pgf.enums.Color

object ChartExamples extends App {

  Chart.pie("test1")(1,1,1,1,1,2,2,2,2,4,4,4,4,6,7,43,3,3)
    .havingText(TextLocation.PIN)
    .havingExplodeFactor(0.2)
    //.havingColors(Color.PINK, Color.LIME, Color.MAGENTA)
    .magnify
    .show()

  sys.exit()
}
