(ns bakery.events-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [bakery.events :as under-test]))

(deftest events-test
  (testing "update-cart adds item"
    (let [expected {:cart {"b" {:price 1 :bulk nil :amount 1}}}
          actual (under-test/update-cart {} [:event-name "b" 1 nil])]
      (is (= expected actual))))
  (testing "update-cart increments count"
    (let [expected {:cart {"b" {:price 1 :bulk nil :amount 2}}}
          actual (under-test/update-cart {:cart {"b" {:price 1 :bulk nil :amount 1}}}
                                         [:event-name "b" 1 nil])]
      (is (= expected actual)))))
