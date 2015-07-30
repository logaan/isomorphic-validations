(ns isomorphic-validations.templates)

(defn field [errors type label name]
  [:p
   [:label label ":" [:br]
    [:input {:type type :name name :id name}]]
   (for [error (errors [name])]
     [:p {:class "error"}
      error])])

(defn form [errors]
  [:div
   [:style {:type "text/css"} ".error {color: red;}"]
   [:h1 "Signup"]
   [:form {:action "/post" :method "POST" :id "signup"}
    (field errors :input "Full Name" :full-name)
    (field errors :input "Email" :email)
    (field errors :input "Desired Username" :username)
    (field errors :password "Password" :password)
    (field errors :password "Confirm Password" :confirm-password)
    [:input {:type "submit"}]]
   [:script {:src "compiled.js"}]])
