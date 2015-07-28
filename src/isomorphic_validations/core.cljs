(ns isomorphic-validations.core
  (:require [isomorphic-validations.validations :refer [user-errors]]
            [vlad.core :refer [validate]]
            [cljs.pprint :refer [pprint]]))

(enable-console-print!)

(defn validate-form []
  (let [user {:fullname (aget (js/document.getElementById "fullname") "value")
              :email (aget (js/document.getElementById "email") "value")
              :username (aget (js/document.getElementById "username") "value")
              :password (aget (js/document.getElementById "password") "value")
              :confirm-password (aget (js/document.getElementById "confirm-password") "value")}]
    (pprint (user-errors user))
  false))

(aset (js/document.getElementById "signup") "onsubmit" validate-form)
