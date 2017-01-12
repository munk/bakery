(ns bakery.db)

(def default-db
  {:name "re-frame"
   :treats [{:id 1
             :name "Brownie"
             :imageURL "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHdr1eTXEMs68Dx-b_mZT0RpifEQ8so6A1unRsJlyJIPe0LUE2HQ"
             :price 2.0
             :bulkPricing {:totalPrice 7.00
                           :amount 4}}]})
