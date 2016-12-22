package Models

/**
  * Created by Mark on 20.12.2016.
  */
import org.joda.time.DateTime
import play.api.libs.json.Json

import scala.reflect.io.{Directory, File}

case class TimeSeriesPoint(time: DateTime, views: Int)

object TimeSeriesModel {
  private val dataFolder = Directory("/home/edgar/tweets/")

  private def getOccurrencesInFile(file: File, word: String): Int = {
    val bufferedSource = scala.io.Source.fromFile(file.path)
    val line = bufferedSource.getLines().filter(_.toLowerCase().startsWith(word.toLowerCase()))
    var res: Int = 0
    if (line.nonEmpty){
      res = line.next().split(',')(1).toInt
    }
    res
  }

  def getPlotFor(word: String, from: DateTime, to: DateTime=DateTime.now, maxPoints: Int = 30): List[TimeSeriesPoint] ={
    var a = List[TimeSeriesPoint]()
    if(dataFolder.exists && dataFolder.isDirectory){
      val occurrences = dataFolder.dirs.filter(x => (x.name.toLong <= to.getMillis ) && (x.name.toLong <= to.getMillis ))
        .map(dir => (dir.name.toLong, dir.files.filter(_.name.startsWith("part-")).map(getOccurrencesInFile(_, word)).sum)).toList.sorted
      var groupSize: Int = 1
      var maxN = occurrences.size
      if(occurrences.size > maxPoints){
        groupSize = occurrences.size / maxPoints
        maxN = groupSize * maxPoints
      }
      a ++= occurrences.take(maxN).grouped(groupSize).map(x => TimeSeriesPoint(new DateTime(x.head._1), x.map(_._2).sum))
    }
    a
  }

  implicit val timeSeriesPointWrites = Json.writes[TimeSeriesPoint]
  implicit val timeSeriesPointReads = Json.reads[TimeSeriesPoint]
}

