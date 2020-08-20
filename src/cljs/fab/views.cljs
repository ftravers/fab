(ns fab.views
  (:require
   [re-frame.core :as rf]
   [reitit.core :as r]
   [reitit.frontend.easy :as rfe]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(defn nav [{:keys [router current-route]}]
  [:ul
   (for [route-name (r/route-names router)
         :let       [route (r/match-by-name router route-name)
                     text (-> route :data :link-text)]]
     [:li {:key route-name}
      (when (= route-name (-> current-route :data :name))
        "> ")
      ;; Create a normal links that user can click
      [:a {:href (href route-name)} text]])])

(defn router-component [{:keys [router]}]
  (let [current-route @(rf/subscribe [:current-route])]
    [:div
     [nav {:router router :current-route current-route}]
     (when current-route
       [(-> current-route :data :view)])]))

(defn home-page []
  [:div
   [:h1 "Home Pagee"]])

(defn sub-page1 []
  [:div
   [:h2 "Sub-Page"]])

