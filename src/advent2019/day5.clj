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

(defn operation [[stack pointer]] 
    (let [ptr->val (partial nth stack)
        [op1 op2 mode1 mode2] (reverse (utils/digits (ptr->val pointer)))
        first-param (if (= mode1 1) (ptr->val (inc pointer) nil) (ptr->val (ptr->val (inc pointer) nil) nil))
        second-param (if (= mode2 1) (ptr->val (+ pointer 2) nil) (ptr->val (ptr->val (+ pointer 2) nil) nil))
        loc (nth stack (+ pointer 3) nil)]
        (cond (= 9 op1) [stack "END"]
            (= 1 op1) [(assoc stack loc (+ first-param second-param)) (+ pointer 4)]
            (= 2 op1) [(assoc stack loc (* first-param second-param)) (+ pointer 4)]
            (= 3 op1) [(assoc stack (ptr->val (inc pointer) nil) @in-out) (+ pointer 2)]
            (= 4 op1) [(do (reset! in-out first-param) stack) (+ pointer 2)]
            )))

(defn compute 
    ([] (compute [(instack) 0]))
    ([[stack itr-ptr]]
        (if (= itr-ptr "END")
        (println @in-out)
        (compute (operation [stack itr-ptr])))
        ))