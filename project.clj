(defproject hello_world "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.36"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :plugins [[lein-cljsbuild "1.0.5"]]
  :source-paths ["src"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled/" "target"]
  :cljsbuild {:builds
              {:min {:source-paths ["src"]
                     :compiler {:main 'foo.core
                                :output-to "resources/public/js/compiled/main.js"
                                :output-dir "resources/public/js/compiled"
                                :asset-path "js/compiled"
                                :source-maps "resources/public/js/compiled/main.js.map"
                                :optimizations :none}}}})