  
(defproject fab "0.1.0-SNAPSHOT"

  :description "FAB: Fenton and Alex's Base Template"

  :url "https://github.com/aeberts/fab"

  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.758"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.9.2"]
                 [reagent "0.10.0"]
                 [re-frame "0.12.0"]
                 ;[datascript "0.18.10"]
                 ;[re-posh "0.3.1"]
                 [cljs-http "0.1.46"]
                 [day8.re-frame/async-flow-fx "0.1.0"] ;; coordinate async stateful tasks (i.e. during startup)
                 [metosin/reitit "0.4.2"]] ;; data driven routing library
                 ;[instaparse "1.4.10"] ;;
                 ;[devcards "0.2.6"]]


  :plugins [[lein-shell "0.5.0"]] ;; call shell commands

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]


  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :aliases {"dev"          ["with-profile" "dev" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]]
            "devcards"     ["with-profile" "dev" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "watch" "devcards"]]
            "prod"         ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
            "build-report" ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]
            "test-jvm"     ["test"]
            "test-karma"   ["shell" "karma" "start" "--single-run"]
            "gh-pages"     ["shell" "yarn" "gh-pages" "-d" "resources/public"]
            "karma"        ["do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "compile" "karma-test"]
                            ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.0"]
                   [day8.re-frame/re-frame-10x "0.6.4"]
                   [day8.re-frame/tracing "0.5.5"]]

    :source-paths ["dev"]}
   :prod
   {:dependencies [
                   [day8.re-frame/tracing-stubs "0.5.5"]]}}


  :prep-tasks [])