(ns advent2019.day1
  (:require [advent2019.utils :as utils]))



(defn fuel-per-mass [m]
  (- (Math/floor (/ m 3)) 2)
)

(defn recursive-fuel [m]
  (let [fuel (fuel-per-mass m)]
  (if (neg? fuel) 0 (+ fuel (recursive-fuel fuel)))))


(defn run []
  (let [input (utils/day-file 1)]
    {:part1 (reduce + (map fuel-per-mass (map #(Integer/parseInt %) input)))
     :part2 (reduce + (map recursive-fuel (map #(Integer/parseInt %) input)))}
    ))