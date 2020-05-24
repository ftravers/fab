(ns fab.db
  (:require [re-frame.core :as rf]))

(def initial-app-db
         {:auth {:uid nil}
          :nav {:active-page :home
                :active-nav :home
                :active-modal nil}
          :current-route :home
          :loading true
          :errors {}
          :users {"barny@mailinator.com" {:uid "barney@mailinator.com"
                                          :profile {:first-name "Barney"
                                                    :last-name "Rubble"}
                                          :role :user}
                  "fred@mailinator.com" {:uid "fred@mailinator.com"
                                         :profile {:first-name "Fred"
                                                   :last-name "Flintstone"}
                                         :role :user}}})

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   initial-app-db))
