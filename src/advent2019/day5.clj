(ns advent2019.day5
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(def INPUT (utils/day-file 5))

(defn split-input [input]
    (s/split input #","))

(defn instack []
    (->> INPUT
        split-input
        (map read-string)
        (into [])))

(def in-out (atom 1))

(def immediate? #(= 1 %))

(defn operation [[stack itr-ptr]] 
    (do (println itr-ptr))
    (let [[op1 op2 mode2 mode1] (utils/digits (nth stack itr-ptr))
        first-param (get [(nth stack (nth stack (inc itr-ptr))) (nth stack (inc itr-ptr))] (or mode1 0))
        second-param (get [(nth stack (nth stack (+ itr-ptr 2))) (nth stack (+ itr-ptr 2))] (or mode2 0))
        loc (nth stack (+ itr-ptr 3) nil)]
        (println [(nth stack (nth stack (+ itr-ptr 2))) op1 op2 mode2 mode1 first-param second-param loc])
        (cond (= 9 op1) [stack "END"]
            (= 1 op1) [(assoc stack loc (+ first-param second-param)) (+ itr-ptr 4)]
            (= 2 op1) [(assoc stack loc (* first-param second-param)) (+ itr-ptr 4)]
            (= 3 op1) [(assoc stack first-param @in-out) (+ itr-ptr 2)]
            (= 4 op1) [(do (reset! in-out first-param) stack) (+ itr-ptr 2)]
            )))

(defn compute 
    ([] (compute [(instack) 0]))
    ([[stack itr-ptr]]
        (println stack)
        (if (= itr-ptr "END")
        (println @in-out)
        (compute (operation [stack itr-ptr])))
        ))