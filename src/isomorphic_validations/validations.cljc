(ns isomorphic-validations.validations
  (:require [vlad.core :refer [join attr present matches equals-field
                               validate guess-field-names translate-errors
                               english-translation]]))

(def validations
  (join (attr [:full-name] (present))
        (attr [:email]    (present))
        (attr [:username] (present))
        (attr [:password] (present))
        (equals-field [:password] [:confirm-password])))

; Should be in vlad
(defn field-errors [user]
  (-> (validate validations user)
      (guess-field-names)
      (translate-errors english-translation)))
