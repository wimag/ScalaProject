package controllers

import Models.TimeSeriesModel._
import Models.TimeSeriesModel
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def stats(word: String, from: Long, to: Long) = Action { implicit request =>
    Ok(Json.toJson(TimeSeriesModel.getPlotFor(word, new DateTime(from), new DateTime(to))))
  }
}