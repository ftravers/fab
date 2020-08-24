(ns fab.events
  (:require
   [fab.db :as db]
   [re-frame.core :refer [reg-event-db]]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(reg-event-db
 :init-rfdb
 (fn-traced [_ _] db/rfdb))
