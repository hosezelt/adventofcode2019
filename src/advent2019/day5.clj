(ns advent2019.day5
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))


(def INPUT (utils/day-file 5))

(defn split-input [input]
    (s/split input #","))