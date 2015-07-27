(ns isomorphic-validations.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.pprint :refer [pprint]]))

(defn field [type label name]
  [:p
   [:label label ":" [:br]
    [:input {:type type :name name}]]])

(defonce users
  (atom []))

(defroutes app
  (GET "/" request
       (html
        [:script {:src "compiled.js"}]
        [:h1 "Signup"]
        [:form {:action "/post" :method "POST"}
         (field :input "Full Name" "fullname")
         (field :input "Email" "email")
         (field :input "Desired Username" "username")
         (field :password "Password" "password")
         (field :password "Confirm Password" "confirm-password")
         [:input {:type "submit"}]]))

  (POST "/post" request
        (swap! users conj (:params request))
        (let [flash "User Created."]
        (html
         [:h1 "Users"]
         [:h3 flash]
         [:ol
          (for [user @users]
          [:li [:pre (with-out-str (pprint user))]])]))))

(def site
  (wrap-defaults app site-defaults))

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
