(ns isomorphic-validations.validations
  (:require [vlad.core :refer [join attr present equals-field]]))

(def validations
  (join (attr [:full-name] (present))
        (attr [:email]     (present))
        (attr [:username]  (present))
        (attr [:password]  (present))
        (equals-field [:password] [:confirm-password])))
