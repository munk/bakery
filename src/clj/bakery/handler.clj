(ns bakery.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/data" [] (resource-response "products-data.json"))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
