(ns bakery.views
    (:require [re-frame.core :as re-frame]))

(defn treat-detail [id]
  (let [treat (re-frame/subscribe [:treats 1])]
    (fn []
      (println @treat)
      [:div {:id "treat-detail"
             :text-align :center
             :style {:height "100px"
                     :width "250px"
                     :position :relative}}
       [:img {:src (:imageURL @treat)
              :style {:float :left
                      :height "100px"
                      :width "100px"}}]
       [:div {:style {:padding-left 110}}
        [:p (:name @treat)]
        [:p "$" (.toFixed (:price @treat) 2)
         (when (:bulkPricing @treat)
           (let [price (get-in @treat [:bulkPricing :totalPrice])
                 amount (get-in @treat [:bulkPricing :amount])]
             (str " or " amount " for $" (.toFixed price 2))))]
        [:button  {:style {:position :absolute
                           :bottom 0}}
         "Add to Cart"]]])))

(defn main-panel []
  (fn []
    [treat-detail 1]))
