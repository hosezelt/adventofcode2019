(ns advent2019.day9
  (:require [advent2019.day5 :as day5]
            [advent2019.utils :as utils]))

(defn extend-memory [prog]
  (into prog (repeat 10000 0)))

(def INPUT (utils/day-file 9))

(defn boost [instruction]
  (let [input (extend-memory (day5/int-codes INPUT))
        in    (atom [instruction])
        out   (atom [])
        in-f  (fn [] (first @in))
        out-f #(swap! out conj %)]
    (day5/compute {:program input :in-f in-f :out-f out-f :pointer 0 :relative-base 0})
    (->> @out)))

(defn solve-1 [] 
  (boost 1))

(defn solve-2 []
  (boost 2))
