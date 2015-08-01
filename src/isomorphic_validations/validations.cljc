(ns isomorphic-validations.validations
  (:require [vlad.core :refer [join chain attr present matches equals-field]]))

(def validations
  (join (attr [:full-name] (present))
        (attr [:email]     (chain (present)
                                  (matches #"@")))
        (attr [:username]  (present))
        (attr [:password]  (present))
        (equals-field [:password] [:confirm-password])))
