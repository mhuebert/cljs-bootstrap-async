(defproject hello_world "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.21"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :plugins [[lein-cljsbuild "1.0.5"]]
  :cljsbuild {:builds
              {:min {:source-paths ["src"]
                     :compiler {:output-to "out/main.js"
                                :output-dir "out/"
                                :optimizations :none}}}})