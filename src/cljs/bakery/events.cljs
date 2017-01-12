(ns bakery.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [bakery.db :as database]))

(re-frame/reg-event-db
 :error-handler
 (fn [db _]
   (js/console.log "wtf")
   db))

(re-frame/reg-event-db
 :initialize-db
 (fn  [db [_ response]]
   (assoc db :treats (:treats response))))

(re-frame/reg-event-fx
 :initialize-app
 (fn [{db :db} _]
   {:http-xhrio {:method          :get
                 :uri             "/data"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [:initialize-db]
                 :on-failure      [:error-handler]}
    :db database/default-db}))
