(ns bakery.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :products
 (fn [db [] id]
   nil))

(re-frame/reg-sub
 :treats
 (fn [db [_ id]]
   (first
    (filter #(= id (:id %))
            (:treats db)))))
