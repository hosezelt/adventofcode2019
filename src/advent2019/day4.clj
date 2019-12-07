(ns advent2019.day4)

(def INPUT {:min 125730 :max 579381})

(defn digits [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn increasing? [numb] (= (sort numb) numb))

(defn multiple? [numb] (apply (complement distinct?) numb))

(defn is-eligible? [numb]
    (->> (digits numb)
        increasing?
        (and multiple?)))

(defn run []
    {:part1 (count (filter is-eligible? (range (:min INPUT) (:max INPUT))))})