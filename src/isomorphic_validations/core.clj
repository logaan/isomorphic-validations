(ns isomorphic-validations.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.pprint :refer [pprint]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [isomorphic-validations.validations :refer :all]))

(defn field [errors type label name]
  [:p
   [:label label ":" [:br]
    [:input {:type type :name name :id name}]]
   (for [error (errors [name])]
     [:p {:style "color: red;"}
      error])])

(defonce users
  (atom []))

(defn inspect [data]
  (with-out-str (pprint data)))

(defn form [errors]
  (html
    [:h1 "Signup"]
    [:form {:action "/post" :method "POST" :id "signup"}
     (anti-forgery-field)
     (field errors :input "Full Name" :fullname)
     (field errors :input "Email" :email)
     (field errors :input "Desired Username" :username)
     (field errors :password "Password" :password)
     (field errors :password "Confirm Password" :confirm-password)
     [:input {:type "submit"}]]
    [:script {:src "compiled.js"}]))

(defroutes app
  (GET "/" request
       (form {}))
  (POST "/post" request
        (let [user (:params request)
              errors (user-errors user)]
          (if (empty? errors)
            (do (swap! users conj user)
                (html
                 [:h1 "Users"]
                 [:h3 "User Created."]
                 [:ol
                  (for [user @users]
                    [:li [:pre (inspect user)]])]))
            (form errors)))))

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
