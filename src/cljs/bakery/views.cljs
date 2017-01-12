(ns bakery.views
    (:require [re-frame.core :as re-frame]))

(defn treat-thumbnail [url]
  [:img {:src url
         :style {:float :left
                 :height "100px"
                 :width "100px"}}])

(defn treat-detail [name price bulkPricing]
  [:div {:style {:padding-left 110}}
   [:p {:style {:padding-top 10}} name]
   [:p "$" (.toFixed price 2)
    (when bulkPricing
      (let [{:keys [totalPrice amount]} bulkPricing]
        (str " or " amount " for $" (.toFixed totalPrice 2))))]
   [:button  {:style {:position :absolute
                      :bottom 0}}
    "Add to Cart"]])

(defn treat-component [id]
  (let [treat (re-frame/subscribe [:treats 1])]
    (fn []
      (let [{:keys [name price imageURL bulkPricing]} @treat]
        [:div {:id "treat-detail"
               :style {:height "100px"
                       :width "250px"
                       :position :relative}}
         [treat-thumbnail imageURL]
         [treat-detail name price bulkPricing]]))))

(defn main-panel []
  (fn []
    [treat-component 1]))
