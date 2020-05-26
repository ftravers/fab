(ns fab.router
  (:require
   [re-frame.core :refer [subscribe dispatch reg-sub reg-event-fx reg-event-db reg-fx]]
   [reitit.frontend :as rfe]
   [reitit.frontend.history :as rfeh]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfee]
   [reitit.dev.pretty :as rpretty]
   [fab.views :as views]))

;; subs
(reg-sub
 :current-route
 (fn [db]
   (:current-route db)))

;; events
(reg-event-fx
 :navigate
 (fn [_ [_ & route]]
   {:navigate! route}))

;; Triggering navigation from events.
(reg-fx
 :navigate!
 (fn [route]
   (apply rfeh/push-state route)))

(reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))

;; router definition

(def routes
  [["/"
    {:name :main-page
     :view views/welcome}]])

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
   {:use-fragment true}))