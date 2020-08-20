(ns fab.randd)

(def task-db
  {1 {:desc "Write spy novel"
      :status :not-started
      :notes ""
      :visibility true
      :children [2 3 4]}
   2 {:desc "Research source material"
      :status :not-started
      :notes ""
      :visibility true
      :children []}
   3 {:desc "Watch All Bond Movies"
      :status :done
      :notes ""
      :visibility true
      :children []}
   4 {:desc "Watch other spy movies"
      :status :not-started
      :notes ""
      :visibility true
      :children [5]}
   5 {:desc "Watch Austin Powers"
      :status :not-started
      :notes ""
      :visibility true
      :children []}})

(defn get-children
  "takes a task-db and a task id and returns a vector of children of the task
  or an empty vector if there are none"
  [task-db t]
  (let [childs (get-in task-db [t :children])]
    childs))

(defn in?
  "true if coll contains elm, else nil"
  [coll elm]
  (some #(= elm %) coll))

(defn dissoc-in
  "Dissociates an entry from a nested associative structure returning a new
  nested structure. keys is a sequence of keys. Any empty maps that result
  will not be present in the new structure."
  [m [k & ks :as keys]]
  (if ks
    (if-let [nextmap (get m k)]
      (let [newmap (dissoc-in nextmap ks)]
        (if (seq newmap)
          (assoc m k newmap)
          (dissoc m k)))
      m)
    (dissoc m k)))

(defn vec-remove
  "remove elem in coll at idx"
  [coll idx]
  (vec (concat (subvec coll 0 idx) (subvec coll (inc idx)))))

(defn vec-add
  "adds a elem to a vector at position pos, if no position is given adds to the end of the vector"
  ([coll elem]
   (conj coll elem))
  ([coll pos elem]
   (vec (concat (conj (subvec coll 0 pos) elem) (subvec coll pos)))))

;; Re-parent Child Notes
;; On the surface, this function is trivial because all it requires is to dissoc
;; the child from one parent's child vector and assoc it to the other.
;; What to do about errors?
;; What if the child doesn't exist in parent-a?
;; What if parent-b doesn't exist?

(defn re-parent-child
  "takes a task-db, the index of the child in :children vector and 2 parent task ids.
  Returns a new task-db with the child moved from parent-a to parent-b
  Will return an error if the child doesn't exist in parent-a
  or if parent-a or parent-b don't exist"
  [task-db source-idx parent-a dest-idx parent-b]
  (let [child-val ((get-children task-db parent-a) source-idx)]
    (cond
      (not (in? (keys task-db) parent-a)) (println (str "Error: parent number " parent-a " is not in the given task-db"))
      (not (in? (keys task-db) parent-b)) (println (str "Error: parent number " parent-b " is not in the given task-db"))
      (not (contains? (get-children task-db parent-a) source-idx)) (println (str "parent-a has no child in position " source-idx))
      :else (-> task-db
                (update-in [parent-b :children] vec-add dest-idx child-val)
                (update-in [parent-a :children] vec-remove source-idx)))))

(comment
 (update-in task-db [1 :children] vec-add 3 :b)
 (vec-add [0 1 2 3 4] 2 :b)
 (vec-remove [1 2 3 4] 0)
 (get-in task-db [1 :children])

 (identity task-db)
 (not (in? (keys task-db) 1))

 ;; take the 0 element of parent 4 and put it in the 3rd position of parent 1
 (re-parent-child task-db 0 4 3 1)

 ;;
 (contains? (get-children task-db 3) 0)
 ;; take the 0 element of parent 3 and put it in the 2rd position of parent 2
 (re-parent-child task-db 0 3 2 2)

 ;; remove child at index 0
 (update-in task-db [1 :children] vec-remove 0)

 ;; add child at index 0
 (update-in task-db [parent-b :children] conj child-val)

 ((get-children task-db 1) 0)

 ;;
 (update-in task-db [5 :children] conj 1)
 (get-children task-db 1)
 (get-children task-db 2)

 _#())