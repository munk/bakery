(ns bakery.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [bakery.core-test]))

(doo-tests 'bakery.core-test)
