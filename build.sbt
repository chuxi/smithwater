name := "smithwaterman"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.3.1"

updateOptions := updateOptions.value.withCachedResolution(true)