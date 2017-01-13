(ns bakery.views
  (:require [re-frame.core :as re-frame]
            [bakery.util :refer [subtotal cart-total]]))

(defn pad [line-len other-chars-len]
  (.repeat "." (- line-len other-chars-len)))

(defn clear-cart []
  (re-frame/dispatch [:clear-cart]))

(defn treat-thumbnail [url]
  [:img.thumb {:src url}])

(defn treat-detail [name price bulkPricing]
  [:div {:style {:padding-left 110}}
   [:p {:style {:padding-top 10}} name]
   [:p "$" (.toFixed price 2)
    (when bulkPricing
      (let [{:keys [totalPrice amount]} bulkPricing]
        (str " or " amount " for $" (.toFixed totalPrice 2))))]
   [:button.product-btn {:on-click #(re-frame/dispatch [:update-cart name price bulkPricing])}
    "Add to Cart"]])

(defn treat-component [id]
  (let [treat (re-frame/subscribe [:treats id])]
    (fn []
      (let [{:keys [name price imageURL bulkPricing]} @treat]
        [:div.treat-detail {:id (str "treat-detail" id)}
         [treat-thumbnail imageURL]
         [treat-detail name price bulkPricing]]))))

(defn cart-entry [name product]
  (let [{:keys [price amount]} product]
    (let [subt (subtotal product)
          subt-len (count (str subt))
          name' (if (= amount 1)
                  name
                  (str name " × " amount))
          name-len (count name')
          padding (pad 50  (+ name-len subt-len))]
      ^{:key name} [:div.cart-entry name' padding "$" (subtotal product)])))

(defn shopping-cart []
  (let [products (re-frame/subscribe [:products])]
    (fn []
      (let [cart-keys (keys @products)
            cart-items @products
            cart-total (cart-total cart-items)
            padding (pad 45 (count (str cart-total)))]
        [:div.cart
         [:div {:style {:padding "10px"}}
          [:h2 "Cart"]
          [:div.cart-entry-container
           (map #(cart-entry % (get cart-items %)) cart-keys)]
          [:div {:style {:font-family "monospace"}} "Total" padding "$" cart-total]
          [:button {:on-click #(do
                                 (js/alert (str "You spent $" cart-total))
                                 (clear-cart))}
           "Checkout"]
          [:button {:on-click clear-cart
                    :style {:float :right}} "Clear"]]]))))

(defn main-panel []
  (let [treats (re-frame/subscribe [:all-treats])]
    (fn []
      [:div
       [:div.product-container
        (map (fn [element]
               ^{:key element} [treat-component (:id element)]) @treats)]
       [:div.cart-container
        [shopping-cart]]])))
