package ru.spbau.mit.extractor

import org.apache.spark.streaming.Duration
import org.apache.spark.streaming.dstream.DStream

import scala.compat.Platform
/**
  * Created by Mark on 20.12.2016.
  */
object Extractor {
  val util = Util.instance

  // Transforms a stream into a hash count map
  def count(stream:DStream[String], windowDuration: Duration): DStream[(String, Int)] =
    stream
      .flatMap(text => text.split(" ").filter(_.matches("^[a-zA-Z0-9]*$")))   // extract alphanumeric words
      .map((_, 1)).reduceByKeyAndWindow(_ + _, windowDuration, slideDuration = windowDuration)
      .map{case (topic, count) => (topic, count)}
      .transform(_.sortBy(pair => pair._2, ascending = false))

  def main(args: Array[String]) {

    val ssc = util.streamContext("extractor")

    val stream = util.stream(ssc).map(status => status.getText)

    // Count the most popular hashtags
    val topCounts = count(stream, util.getSeconds("twitter.tag.seconds"))

    // Output directory
    val outputDir = util.conf.getProperty("output.dir", "E:\\Large stuff\\tweets\\data\\window")

    // Prints to the output directory
    def saveFrequencies(counts:DStream[(String, Int)]) =
      counts.foreachRDD(rdd => {
        rdd.map(a => a._1.toLowerCase() + "," + a._2).saveAsTextFile(outputDir + "\\" + Platform.currentTime)
      })

    saveFrequencies(topCounts)

    ssc.start()
    ssc.awaitTermination()
  }
}
