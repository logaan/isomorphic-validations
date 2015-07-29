(ns isomorphic-validations.core
  (:require [isomorphic-validations.validations :refer [field-errors]]))

(def validate-clientside?
  (atom true))

(defn append-error [field-name text]
  (let [node (aget (js/document.getElementById field-name) "parentElement")
        error (js/document.createElement "p")]
    (.appendChild error (js/document.createTextNode text))
    (aset (aget error "style") "color" "red")
    (.appendChild node error)))

(defn validate-form []
  (if @validate-clientside?
    (let [user {:full-name        (aget (js/document.getElementById "full-name") "value")
                :email            (aget (js/document.getElementById "email") "value")
                :username         (aget (js/document.getElementById "username") "value")
                :password         (aget (js/document.getElementById "password") "value")
                :confirm-password (aget (js/document.getElementById "confirm-password") "value")}]
     (doall
      (for [[field errors] (field-errors user)
            error errors]
        (append-error (name (first field)) error)))
     (reset! validate-clientside? false)
     false)))

(aset (js/document.getElementById "signup") "onsubmit" validate-form)

