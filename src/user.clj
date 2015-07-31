(ns user
  (:require [isomorphic-validations.core :refer [site]]
            [org.httpkit.server :refer [run-server]]))

(defonce server
  (atom nil))

(defn start-server []
  (reset! server (run-server #'site {:port 8080})))

(defn stop-server []
  (@server))

(defn restart-server []
  (stop-server)
  (start-server))
