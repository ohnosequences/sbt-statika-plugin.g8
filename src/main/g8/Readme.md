### sbt-statika-$org$ plugin

To use it in your bundle, add this to `project/plugins.sbt`:

```scala
resolvers ++= Seq(
  Resolver.url("Era7 releases", url("http://releases.$publishing_suffix$.s3.amazonaws.com"))(Resolver.ivyStylePatterns)
)

addSbtPlugin("$org$" % "sbt-statika-$org$" % "0.1.0")
```
