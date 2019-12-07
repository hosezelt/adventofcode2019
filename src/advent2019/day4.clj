(ns advent2019.day4)

(def INPUT {:min 125730 :max 579381})

(defn digits [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn increasing? [n] (= (sort n) n))

(defn multiple? [n] (not= (dedupe n) n))

(defn is-eligible? [n]
    (and (increasing? n) 
      (multiple? n)))

(defn pairs? [n]
  (some #{2} (vals (frequencies n))))

(defn is-elig? [n]
    (and (increasing? n)
      (pairs? n)))

(defn run []
    {:part1 (count (filter is-eligible? (map digits (range (:min INPUT) (:max INPUT)))))
     :part2 (count (filter is-elig? (map digits (range (:min INPUT) (:max INPUT)))))})

(def map-func (map #(count (filter % (map digits (range (:min INPUT) (:max INPUT))))) [is-elig? is-eligible?]))