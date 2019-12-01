(ns advent2019.day1
  (:require [advent2019.utils :as utils]))



(defn fuel-required [s]
  (- (Math/floor (/ (Integer/parseInt s) 3)) 2) 
)


(defn run []
  (let [input (utils/day-file 1)]
    {:part1 (reduce + (map fuel-required input))}
    ))