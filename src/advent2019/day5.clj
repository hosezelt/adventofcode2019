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

(defn operation [[stack itr-ptr]] 
    (do (println itr-ptr))
    (let [opcode (nth stack itr-ptr)
        first-param (nth stack (inc itr-ptr) nil)
        second-param (nth stack (+ itr-ptr 2) nil)
        loc (nth stack (+ itr-ptr 3) nil)]
        (cond (= 99 opcode) [stack "END"]
            (= 1 opcode) [(assoc stack loc (+ (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
            (= 2 opcode) [(assoc stack loc (* (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
            (= 3 opcode) [(do (assoc stack first-param @in-out) stack) (+ itr-ptr 2)]
            (= 4 opcode) [(do (reset! in-out (nth stack first-param)) stack) (+ itr-ptr 2)]
            (= 1101 opcode) [(assoc stack loc (+ first-param second-param)) (+ itr-ptr 4)]
            (= 0101 opcode) [(assoc stack loc (+ first-param (nth stack second-param))) (+ itr-ptr 4)]
            (= 1001 opcode) [(assoc stack loc (+ (nth stack first-param) second-param)) (+ itr-ptr 4)]
            (= 1102 opcode) [(assoc stack loc (* first-param second-param)) (+ itr-ptr 4)]
            (= 0102 opcode) [(assoc stack loc (* first-param (nth stack second-param))) (+ itr-ptr 4)]
            (= 1002 opcode) [(assoc stack loc (* (nth stack first-param) second-param)) (+ itr-ptr 4)]
            (= 104 opcode) [(do (reset! in-out first-param) stack) (+ itr-ptr 2)]
            )))

(defn compute []
    (loop [[stack itr-ptr] [(instack) 0]]
        (if (= itr-ptr "END")
        (println stack)
        (recur (operation [stack itr-ptr])
        ))))