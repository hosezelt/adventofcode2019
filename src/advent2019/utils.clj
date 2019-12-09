(ns advent2019.utils
    (:require [clojure.java.io :as io]
              [clojure.string :as str]))
  
(defn day-file
  ([day] (day-file day nil))
  ([day part]
  (->> (io/resource (str "day" day (if part (str "-" part)) ".txt"))
    (slurp))))
          
(defn digits [n]
  (->> n
  (iterate #(quot % 10))
  (take-while pos?)
  (mapv #(mod % 10))
  rseq))