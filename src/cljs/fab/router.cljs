(ns fab.router
  (:require
   [re-frame.core :refer [dispatch subscribe reg-sub reg-event-fx reg-event-db reg-fx]]
   [reitit.frontend :as rfe]
   [reitit.frontend.history :as rfeh]
   [reitit.frontend.controllers :as rfc]
   [reitit.coercion.spec :as rss]
   [reitit.frontend.easy :as rfee]
   [reitit.dev.pretty :as rpretty]
   [fab.views :as views]
   [reitit.core :as r]))

;; subs
(reg-sub
 :current-route
 (fn [db]
   (:current-route db)))

;; events
(reg-event-fx
 :navigate
 (fn [db [_ & route]]
   {:navigate! route}))

(reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))

;; Triggering navigation from events.
(reg-fx
 :navigate!
 (fn [route]
   (apply rfee/push-state route)))

;; Routes

(def routes
  ["/"
   [""
    {:name :home
     :view views/home-page
     :link-text "Home"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering home page"))
       :stop (fn [& params] (js/console.log "Leaving home page"))}]}]
   ["sub-page1"
    {:name :sub-page1
     :views views/sub-page1
     :link-text "Sub-page 1"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering sub-page1"))
       :stop (fn [& params] (js/console.log "Leaving sub-page1"))}]}]])

(def router
  (rfe/router
   routes
   {:exception rpretty/exception}))

(defn on-navigate [new-match]
  (when new-match
    (dispatch [:navigated new-match])))

(defn init-routes! []
  (prn "Initializing routes")
  (rfee/start!
   router
   on-navigate
   ;; set {:use-fragment} to false to enable reitit HistoryAPI
   ;; reitit push-state changes the route
   ;; reitit replace-state change route without leaving previous entry in the browser
   ;; https://metosin.github.io/reitit/frontend/browser.html
   {:use-fragment false}))
