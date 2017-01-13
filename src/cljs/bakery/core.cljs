(ns bakery.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [bakery.events]
              [bakery.subs]
              [bakery.views :as views]
              [bakery.config :as config]))

(enable-console-print!)

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-app])
  (dev-setup)
  (mount-root))
