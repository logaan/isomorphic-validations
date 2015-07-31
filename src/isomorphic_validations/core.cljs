(ns isomorphic-validations.core
  (:require [vlad.core :refer [field-errors]]
            [isomorphic-validations.validations :refer [validations]]
            [isomorphic-validations.templates :refer [form]]
            [reagent.core :as reagent]
            [goog.dom :as dom]
            [goog.dom.forms :as forms]))

(def errors
  (reagent/atom {}))

(defn form-data [form-id]
  (let [fields-to-arrays (.toObject (forms/getFormDataMap (dom/getElement form-id)))]
    (into {} (for [[k v] (js->clj fields-to-arrays)] [(keyword k) (first v)]))))

(defn validate-form []
    (let [user-errors (field-errors validations (form-data "signup"))]
      (if (empty? user-errors)
        true
        (do (reset! errors user-errors)
            false))))

(reagent/render-component [#(form @errors)] (.-body js/document))
(aset (js/document.getElementById "signup") "onsubmit" validate-form)
