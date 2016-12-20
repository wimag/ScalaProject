Fear and loathing in Academic University

# Spark howto

## Export directory for properties file
export SPARK_CONF_DIR=C:/Users/Mark/Documents/java/Scala_project/src/main/resources/

##if not on windows - remove
```
System.setProperty("hadoop.home.dir", "c:\\winutils")
```

in Analyzer.scala and Extractor.scala change output directoreis (temp solution for now)

On Unix systems, in util.scale remove winutils declaration

##Run with:
C:\spark\spark-2.0.1-bin-hadoop2.7\bin\spark-submit --class ru.spbau.mit.extractor.Extractor target\scala-2.11\Scala_project-assembly-1.0.jar

##Build jar
```
sbt backend build
```

# For frontend:
##Libararies
Install jsdom

```
npm.install jsdom
```

Build js packages:
```
sbt backend/run
```

# Display

Simply run play

#TODO:
* Modify Example:
    * Make request to localhost with params `(word, from=system_millis, to=system_millis)`
    * Plot graphs
* Optimize sbt for mackend to not generate EVERYTHING in public/javascripts
* Remove internal constant definitions (Move them to properties)