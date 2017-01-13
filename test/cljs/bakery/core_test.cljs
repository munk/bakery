(ns bakery.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [bakery.util :as under-test]))

(deftest subtotal-test
  (testing "one brownie costs $2"
    (let [expected 2
          actual (under-test/subtotal {:price 2 :amount 1})])
    (is (= expected actual)))

  (testing "two brownies cost $4"
    (let [expected 4
          actual (under-test/subtotal {:price 2 :amount 2})]
      (is (= expected actual))))

  (testing "four brownies cost $7"
    (let [expected 7
          actual (under-test/subtotal {:price 2
                                       :amount 4
                                       :bulk {:amount 4
                                              :totalPrice 7}})]
      (is (= expected actual))))

  (testing "four brownies and 3 cookies cost 8.50"
    (let [expected 8.5
          actual (under-test/cart-total
                  {"Brownie" {:price 2 :amount 4 :bulk {:amount 4 :totalPrice 7}}
                   "Cookie" {:price 0.5 :amount 3 :bulk nil}})]
      (is (= expected actual))))


  ;;; Acceptance tests

  (testing "Cookie, Brownie x 4, Cheesecake costs is $16.25"
    (let [expected 16.25
          actual (under-test/cart-total
                  {"Cookie" {:amount 1 :price 1.25 :bulk {:amount 6 :totalPrice 6.0}}
                   "Brownie" {:amount 4 :price 2.0 :bulk {:amount 4 :totalPrice 7.0}}
                   "Cheesecake" {:amount 1 :price 8.0 :bulk nil}})]
      (is (= actual expected))))

  (testing "Cookie x 8 costs 8.50"
    (let [expected 8.50
          actual (under-test/cart-total
                  {"Cookie" {:amount 8 :price 1.25 :bulk {:amount 6 :totalPrice 6.0}}})]
      (is (= actual expected))))

  (testing "Cookie, Brownie, Cheesecake, Donut x 2, price is $12.25"
    (let [expected 12.25
          actual (under-test/cart-total
                  {"Cookie" {:amount 1 :price 1.25 :bulk {:amount 6 :totalPrice 6.0}}
                   "Brownie" {:amount 1 :price 2.0 :bulk {:amount 4 :totalPrice 7.0}}
                   "Cheesecake" {:amount 1 :price 8.0 :bulk nil}
                   "Donut" {:amount 2 :price 0.5 :bulk nil}})]
      (is (= actual expected)))))
