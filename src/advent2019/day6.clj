(ns advent2019.day6
    (:require [advent2019.utils :as utils]))

(def test-string "")

(defn parse-map [string]
    (->> string
    (clojure.string/split-lines)
    (map #(clojure.string/split % #"\)"))
    (map #(into {} {(keyword (second %)) (first %)}))))
