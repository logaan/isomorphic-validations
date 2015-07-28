(ns isomorphic-validations.core
  (:require [isomorphic-validations.validations :refer [user-errors]]
            [vlad.core :refer [validate]]
            [cljs.pprint :refer [pprint]]))

(enable-console-print!)

(defn append-error [field-name text]
  (let [node (aget (js/document.getElementById field-name) "parentElement")
        error (js/document.createElement "p")]
    (.appendChild error (js/document.createTextNode text))
    (aset (aget error "style") "color" "red")
    (.appendChild node error)))

(defn validate-form []
  (let [user {:fullname (aget (js/document.getElementById "fullname") "value")
              :email (aget (js/document.getElementById "email") "value")
              :username (aget (js/document.getElementById "username") "value")
              :password (aget (js/document.getElementById "password") "value")
              :confirm-password (aget (js/document.getElementById "confirm-password") "value")}]
    (doall
     (for [[field errors] (user-errors user)
           error errors]
       (append-error (name (first field)) error)))
    false))

(aset (js/document.getElementById "signup") "onsubmit" validate-form)

