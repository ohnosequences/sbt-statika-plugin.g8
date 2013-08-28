import sbt._
import Keys._

import SbtS3Resolver._

object SbtStatika$org;format="Camel"$ extends Plugin with SbtStatikaPlugin {

  // just some local aliases
  private val mvn = Resolver.mavenStylePatterns
  private val ivy = Resolver.ivyStylePatterns

  val suffix = "statika.$org$.com"

  override def projectSettings = 
    sbtStatikaSettings ++ Seq(
      organization := "$org$"
    , organizationHomepage := Some(url("http://$org$.com"))

    , statikaVersion := "0.13.0"

    , instanceProfileARN := None

    , publicResolvers := Seq(
        "Statika public maven releases" at toHttp("s3://releases."+suffix)
      , "Statika public maven snapshots" at toHttp("s3://snapshots."+suffix)
      , Resolver.url("Statika public ivy releases", url(toHttp("s3://releases."+suffix)))(ivy)
      , Resolver.url("Statika public ivy snapshots", url(toHttp("s3://snapshots."+suffix)))(ivy)
      )

    , privateResolvers := Seq(
        S3Resolver("Statika private ivy releases",    "s3://private.releases."+suffix, ivy)
      , S3Resolver("Statika private maven releases",  "s3://private.releases."+suffix, mvn)
      , S3Resolver("Statika private ivy snapshots",   "s3://private.snapshots."+suffix, ivy)
      , S3Resolver("Statika private maven snapshots", "s3://private.snapshots."+suffix, mvn)
      )

    // publishing (ivy-style by default)
    , publishMavenStyle := false
    , publishTo <<= (isSnapshot, s3credentials, isPrivate, publishMavenStyle) { 
                      (snapshot,   credentials,   priv,    mvnStyle) => 
        val privacy = if (priv) "private." else ""
        val prefix = if (snapshot) "snapshots" else "releases"
        credentials map S3Resolver( 
            "Statika "+privacy+prefix+" S3 publishing bucket"
          , "s3://"+privacy+prefix+"."+suffix
          , if(mvnStyle) mvn else ivy
          ).toSbtResolver
      }
    , isPrivate := false
    )
}
