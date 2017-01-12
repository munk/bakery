(ns bakery.db)

(def default-db
  {:loading? false
   :cart {}
   :treats [{:id 1
             :name ""
             :imageURL ""
             :price 0}]})
