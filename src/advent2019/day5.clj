(ns advent2019.day5
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))


(def INPUT (utils/day-file 5))

(defn split-input [input]
    (s/split input #","))

(def opcodes {

})

(defn operation [[stack itr-ptr]] (let [opcode (nth stack itr-ptr)
    first-param (nth stack (inc itr-ptr))
    second-param (nth stack (+ itr-ptr 2))
    loc (nth stack (+ itr-ptr 3))]
    (cond (= 99 opcode) [(get stack 0) "END"]
          (= 1 opcode) [(assoc stack loc (+ (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
          (= 2 opcode) [(assoc stack loc (* (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
          )))

(defn testm [x y] (+ x y))
 
(defn compute [noun verb]
    (loop [[stack itr-ptr] [(instack2 noun verb) 0]]
        (if (= itr-ptr "END")
        (println stack)
        (recur (operation [stack itr-ptr])
        ))))