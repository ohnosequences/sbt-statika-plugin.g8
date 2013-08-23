### $name$ plugin

To use it in your bundle, add this to `project/plugins.sbt`:

```scala
resolvers ++= Seq(
  Resolver.url("Era7 releases", url("http://releases.era7.com.s3.amazonaws.com"))(Resolver.ivyStylePatterns)
)

addSbtPlugin("$org$" % "$name$" % "0.1.0")
```
