(defproject isomorphic-validations "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]
                 [hiccup "1.0.5"]
                 [ring/ring-defaults "0.1.5"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [vlad "3.3.0"]
                 [reagent "0.5.0"]]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :cljsbuild {:builds {:test {:source-paths ["src"]
                              :compiler {:output-to "resources/public/compiled.js"
                                         :optimizations :whitespace
                                         :pretty-print true}}}})
