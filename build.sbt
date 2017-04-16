import Dependencies._

lazy val pluginSettings = Seq(
  bintrayPackage := "sbt-contraband",
  sbtPlugin := true
)

lazy val root = (project in file(".")).
  enablePlugins(NoPublish, TravisSitePlugin).
  aggregate(library, plugin).
  settings(
    inThisBuild(List(
      version := "0.3.0-M4",
      organization := "org.scala-sbt",
      crossScalaVersions := Seq("2.12.1", "2.11.8", "2.10.6"),
      scalaVersion := "2.12.1",
      organizationName := "sbt",
      organizationHomepage := Some(url("http://scala-sbt.org/")),
      homepage := Some(url("http://scala-sbt.org/contraband")),
      licenses += ("Apache-2.0", url("https://github.com/sbt/contraband/blob/master/LICENSE")),
      bintrayVcsUrl := Some("git@github.com:sbt/contraband.git"),
      scmInfo := Some(ScmInfo(url("https://github.com/sbt/contraband"), "git@github.com:sbt/contraband.git")),
      developers := List(
        Developer("eed3si9n", "Eugene Yokota", "@eed3si9n", url("https://github.com/eed3si9n")),
        Developer("dwijnand", "Dale Wijnand", "@dwijnand", url("https://github.com/dwijnand")),
        Developer("Duhemm", "Martin Duhem", "@Duhemm", url("https://github.com/Duhemm"))
      ),
      description := "Contraband is a description language for your datatypes and APIs, currently targeting Java and Scala."
    )),
    name := "contraband root",
    siteGithubRepo := "sbt/contraband",
    siteEmail := { "eed3si9n" + "@" + "gmail.com" }
  )

lazy val library = (project in file("library")).
  enablePlugins(KeywordPlugin, SonatypePublish).
  disablePlugins(BintrayPlugin).
  settings(
    name := "contraband",
    libraryDependencies ++= Seq(parboiled) ++ jsonDependencies ++ Seq(scalaTest % Test, diffutils % Test)
  )

lazy val plugin = (project in file("plugin")).
  enablePlugins(BintrayPublish).
  settings(
    pluginSettings,
    name := "sbt-contraband",
    description := "sbt plugin to generate growable datatypes.",
    // ScriptedPlugin.scriptedSettings,
    // scriptedLaunchOpts := { scriptedLaunchOpts.value ++
    //   Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
    // },
    publishLocal := (publishLocal dependsOn (publishLocal in library)).value,
    sbtVersion in Global := "1.0.0-M4-LOCAL-20170415B",
    scalaCompilerBridgeSource := {
      ("org.scala-sbt" % "compiler-interface" % "0.13.15" % "component").sources
    }
  ).
  dependsOn(library)
