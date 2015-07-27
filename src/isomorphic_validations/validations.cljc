(ns isomorphic-validations.validations
  (:require [vlad.core :refer :all]))

(def validations
  (join (attr [:fullname] (present))
        (attr [:email] (present))
        (attr [:username] (present))
        (attr [:password] (present))
        (attr [:confirm-password] (present))
        (equals-field [:password] [:confirm-password])))

(def field-names
  {[:fullname] "Full Name"
   [:email]    "Email"
   [:username] "Username"
   [:password] "Password"
   [:confirm-password] "Confirm Password"})

