package controllers

import Models.{TimeSeriesModel, TimeSeriesPoint}
import org.joda.time.DateTime
import org.sameersingh.scalaplot.Implicits._
import play.api.mvc._

class Application extends Controller {
  var counter = 0
  val path = new java.io.File(".").getCanonicalPath

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def stats(first: String, second: String, from: Long, to: Long) = Action { implicit request =>
    val ts1 = TimeSeriesModel.getPlotFor(first, new DateTime(from), new DateTime(to))
    val ts2 = TimeSeriesModel.getPlotFor(second, new DateTime(from), new DateTime(to))
    Ok(plotGraph(ts1, ts2, first, second))
  }

  def plotGraph(first: List[TimeSeriesPoint], second: List[TimeSeriesPoint], word1: String, word2: String) : String = {
    val xs = first.map(_.time.getMillis.toDouble)
    val ys1 = first.map(_.views.toDouble)
    val ys2 = second.map(_.views.toDouble)
    val res = output(SVG, xyChart(xs -> Seq(Y(ys1, word1), Y(ys2, word2))))
    counter += 1
    res
  }
}