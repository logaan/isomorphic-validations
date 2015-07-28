(ns isomorphic-validations.validations
  (:require [vlad.core :refer [join attr present matches equals-field
                               validate assign-name translate-errors english-translation]]))

(def validations
  (join (attr [:fullname] (present))
        (attr [:email]  (present))
        (attr [:username] (present))
        (attr [:password] (present))
        (equals-field [:password] [:confirm-password])))

(def field-names
  {[:fullname] "Full Name"
   [:email]    "Email"
   [:username] "Username"
   [:password] "Password"
   [:confirm-password] "Confirm Password"})

(defn user-errors [user]
  (-> (validate validations user)
      (assign-name field-names)
      (translate-errors english-translation)))
