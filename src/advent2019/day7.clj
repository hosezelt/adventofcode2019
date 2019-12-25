(ns advent2019.day7
  (:require [clojure.string :as s]
            [advent2019.day5 :as day5]
            [advent2019.utils :as utils]))

(def INPUT (utils/day-file 7))

(defn split-input [input]
  (s/split input #","))

(defn int-codes [string]
  (->> string
       split-input
       (map read-string)
       (into [])))



(defn solve []
  (let [input (int-codes INPUT)
        in    (atom [5])
        out   (atom [])
        in-f  (fn [] (first @in))
        out-f #(swap! out conj %)]
    (day5/compute {:program input :in-f in-f :out-f out-f :pointer 0})
    (->> @out
         last)))

(solve-1)

