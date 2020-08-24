(ns fab.core
  (:require
   [fab.config :as config]
   [fab.router :as router]
   [fab.views :as views]
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [fab.db :as db]))

(defn dev-setup []
  (when config/debug?
    (println "debug mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (router/init-routes!)
  (rdom/render [views/router-component {:router router/router}]
               (.getElementById js/document "app")))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

(defn -main []
  (init))
