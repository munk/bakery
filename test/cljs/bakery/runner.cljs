(ns bakery.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [bakery.core-test]
              [bakery.events-test]))

(doo-tests 'bakery.core-test
           'bakery.events-test)
