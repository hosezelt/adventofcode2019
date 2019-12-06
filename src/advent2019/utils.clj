(ns advent2019.utils
    (:require [clojure.java.io :as io]
              [clojure.string :as str]))
  
  (defn day-file
    ([day] (day-file day nil))
    ([day part]
     (->> (io/resource (str "day" day (if part (str "-" part)) ".txt"))
          (slurp))))
          