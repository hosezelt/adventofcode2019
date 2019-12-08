(ns advent2019.day2
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(defn split-input [input]
    (s/split input #","))

(defn instack [noun verb] (assoc (assoc (into [] (map read-string (split-input (utils/day-file 2)))) 1 noun) 2 verb))

(defn operation [[stack itr-ptr]] (let [opcode (nth stack itr-ptr)
    first-param (nth stack (inc itr-ptr))
    second-param (nth stack (+ itr-ptr 2))
    loc (nth stack (+ itr-ptr 3))]
    (cond (= 99 opcode) [(get stack 0) "END"]
          (= 1 opcode) [(assoc stack loc (+ (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
          (= 2 opcode) [(assoc stack loc (* (nth stack first-param) (nth stack second-param))) (+ itr-ptr 4)]
          )))
   
(defn compute [noun verb]
    (loop [[stack itr-ptr] [(instack noun verb) 0]]
        (if (= itr-ptr "END")
        stack
        (recur (operation [stack itr-ptr])
        ))))

(def variable-input 
    (let [execs (for [noun (range 100) verb (range 100)] 
        {:noun noun :verb verb :result (compute noun verb)})
        exec (first (filter #(= 19690720 (:result %)) execs))] 
        (+ (* 100 (:noun exec)) (:verb exec))))
    
(defn run []
    {:part1 (compute 12 2)
    :part2 variable-input})
      