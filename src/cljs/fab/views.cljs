(ns fab.views
  (:require
   [re-frame.core :as rf]))

(defn welcome []
  [:h1 "Welcome to FAB!"])

(defn main-page []
  (let [current-route @(rf/subscribe [:current-route])]
    [:div
     (when current-route
       [(-> current-route :data :view)])]))

(defn root []
  [:div#root
   {:style {:width "100vw"}}
   [main-page]])