(ns isomorphic-validations.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.pprint :refer [pprint]]
            [vlad.core :refer [field-errors]]
            [isomorphic-validations.validations :refer [validations]]
            [isomorphic-validations.templates :refer [form]]))

(defonce users
  (atom []))

(defn inspect [data]
  (with-out-str (pprint data)))

(defn user-listing [users]
  (list
   [:h1 "Users"]
   [:h3 "User Created."]
   [:ol
    (for [user users]
      [:li [:pre (inspect user)]])]))

(defroutes app
  (GET "/" request
       (html (form {})))
  (POST "/post" request
        (let [user (:params request)
              errors (field-errors validations user)]
          (html
           (if (empty? errors)
             (do (swap! users conj user)
                 (user-listing @users))
             (form errors))))))

(def site
  (wrap-defaults app (assoc-in site-defaults [:security :anti-forgery] false)))

                                        ; Testing junk

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
