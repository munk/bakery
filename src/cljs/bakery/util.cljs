(ns bakery.util)

(defn subtotal [{:keys [price bulk amount]}]
  (if bulk
    (let [item-ct amount
          {:keys [amount totalPrice]} bulk
          bulk-ct (quot item-ct amount)
          bulk-rem (rem item-ct amount)]
      (if (= 0 bulk-ct)
        (* price item-ct)
        (+ (* bulk-ct totalPrice) (* price bulk-rem))))
    (* price amount)))

(defn cart-total [cart]
  (let [ks (keys cart)
        subs (map #(subtotal (get cart %)) ks)]
    (reduce + subs)))
