(ns bakery.views
    (:require [re-frame.core :as re-frame]))

(enable-console-print!)

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
  (let [treat (re-frame/subscribe [:treats id])]
    (fn []
      (let [{:keys [name price imageURL bulkPricing]} @treat]
        [:div {:id "treat-detail"
               :style {:height "100px"
                       :width "250px"
                       :position :relative}}
         [treat-thumbnail imageURL]
         [treat-detail name price bulkPricing]]))))

(defn cart-entry [{:keys [name amount price bulkPricing]}]
  (fn []
    [:div name "..." "$" (* price amount)]))

(defn shopping-cart []
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:div {:style {:border-style :solid
                     :border-width "1px"
                     :width "200px"}}
       [:div {:style {:padding "10px"}}
        [:h2 "Cart"]
        [cart-entry {:name "Brownie"
                     :amount 3
                     :price 1.25}]
        [:div "Total ..." "$" 9.75]
        [:button "Checkout"]]
       ])))

(defn main-panel []
  (let [treats (re-frame/subscribe [:all-treats])]
    (fn []
      [:div
       [:div {:style {:width "250px"
                      :float :left}}
        (map (fn [element]
               [treat-component (:id element)]) @treats)]
       [:div {:style {:margin-left "300px"}}
        [shopping-cart]]])))
