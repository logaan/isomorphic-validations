(ns isomorphic-validations.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.pprint :refer [pprint]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [vlad.core :refer :all]
            [isomorphic-validations.validations :refer :all]))

(defn field [type label name]
  [:p
   [:label label ":" [:br]
    [:input {:type type :name name}]]])

(defonce users
  (atom []))

(defn inspect [data]
  (with-out-str (pprint data)))

(defroutes app
  (GET "/" request
       (html
        [:script {:src "compiled.js"}]
        [:h1 "Signup"]
        [:form {:action "/post" :method "POST"}
         (anti-forgery-field)
         (field :input "Full Name" "fullname")
         (field :input "Email" "email")
         (field :input "Desired Username" "username")
         (field :password "Password" "password")
         (field :password "Confirm Password" "confirm-password")
         [:input {:type "submit"}]]))

  (POST "/post" request
        (let [user (:params request)
              errors (-> (validate validations user)
                         (assign-name field-names)
                         (translate-errors english-translation))
              flash (if (empty? errors)
                      (do (swap! users conj user)
                          "User Created.")
                      "User Invalid.")]
          (html
           [:h1 "Users"]
           [:h3 flash]
           [:pre (inspect errors)]
           [:ol
            (for [user @users]
              [:li [:pre (inspect user)]])]))))

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
