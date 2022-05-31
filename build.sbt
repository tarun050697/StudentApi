name := """studentProject"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  guice
)
//libraryDependencies += guice
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.11.1"
libraryDependencies +=  "org.projectlombok" % "lombok" % "1.18.2"
libraryDependencies += "org.mongodb" % "mongodb-driver-async" % "3.6.2"

//libraryDependencies += "com.typesafe.play" % "play-json" % "2.6.0"