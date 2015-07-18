(ns isomorphic-validations.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app
  (GET "/" request
       (html
        [:h1 "Hello World"]
        [:form {:action "/post" :method "POST"}
         [:input {:name "fullname"}]
         [:input {:type "submit"}]]))
  (POST "/post" request
        #break (:params request)
        "Done"))

(def site
  (wrap-defaults app api-defaults))

(defonce server
  (atom nil))

(defn start-server []
  (reset! server (run-server #'site {:port 8080})))

(defn stop-server []
  (@server))

(defn restart-server []
  (stop-server)
  (start-server))

(comment

  (start-server)

  (restart-server)

  )
