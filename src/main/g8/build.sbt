import sbtrelease._
import ReleasePlugin._

sbtPlugin := true

name := "$name$"

description := "Version of sbt-statika plugin for $org$"

homepage := Some(url("https://github.com/$org$/$name$"))

organization := "$org$"

organizationHomepage := Some(url("http://$org$.com"))

licenses := Seq("AGPLv3" -> url("http://www.gnu.org/licenses/agpl-3.0.txt"))


scalaVersion := "2.9.2"


publishMavenStyle := false

publishTo <<= (isSnapshot, s3credentials) { 
                (snapshot,   credentials) => 
  val prefix = if (snapshot) "snapshots" else "releases"
  credentials map S3Resolver(
      prefix+" S3 publishing bucket"
    , "s3://"+prefix+"era7.com"
    , Resolver.ivyStylePatterns
    ).toSbtResolver
}

resolvers ++= Seq ( 
    Resolver.typesafeRepo("releases")
  , Resolver.sonatypeRepo("releases")
  , Resolver.sonatypeRepo("snapshots")
  , "Era7 Releases"  at "http://releases.era7.com.s3.amazonaws.com"
//, "Era7 Snapshots" at "http://snapshots.era7.com.s3.amazonaws.com"
  , Resolver.url("Era7 ivy releases", url("http://releases.era7.com.s3.amazonaws.com"))(Resolver.ivyStylePatterns)
//, Resolver.url("Era7 ivy snapshots", url("http://snapshots.era7.com.s3.amazonaws.com"))(Resolver.ivyStylePatterns)
  )

addSbtPlugin("ohnosequences" % "sbt-s3-resolver" % "0.5.2")

addSbtPlugin("ohnosequences" % "sbt-statika" % "0.7.0")


// sbt-release settings
releaseSettings
