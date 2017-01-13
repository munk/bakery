(ns bakery.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [bakery.db :as database]))


(defn error-handler [db _] db)


(defn init-db [db [_ response]]
  (assoc db :treats (:treats response)))


(defn init-app [{db :db}]
  {:http-xhrio {:method          :get
                :uri             "/data"
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success      [:initialize-db]
                :on-failure      [:error-handler]}
   :db database/default-db})


(defn update-cart [db [_ name price bulkPrice]]
  (let [cart (:cart db)
        item (get cart name)]
    (if item
      (assoc-in db [:cart name] (update item :amount inc))
      (assoc-in db [:cart name] {:price price
                                 :bulk bulkPrice
                                 :amount 1}))))

(defn clear-cart [db _]
  (assoc db :cart {}))


(re-frame/reg-event-db
 :error-handler
 error-handler)

(re-frame/reg-event-db
 :initialize-db
 init-db)

(re-frame/reg-event-fx
 :initialize-app
 init-app)

(re-frame/reg-event-db
 :update-cart
 update-cart)

(re-frame/reg-event-db
 :clear-cart
 clear-cart)
