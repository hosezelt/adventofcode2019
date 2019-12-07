(ns advent2019.day2
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(defn split-input [input]
    (s/split input #","))

(def instack (assoc (assoc (into [] (map read-string (split-input (utils/day-file 2)))) 1 12) 2 2))

(defn instack2 [noun verb] (assoc (assoc (into [] (map read-string (split-input (utils/day-file 2)))) 1 noun) 2 verb))

(defn operation [stack count]
(let [instr-set (take 4 (drop count stack))]
    (let [[opcode 
           first-param
           second-param
           loc] instr-set]
    (cond (= 99 opcode) (reduced stack)
          (= 1 opcode) (assoc stack loc (+ (nth stack first-param) (nth stack second-param)))
          (= 2 opcode) (assoc stack loc (* (nth stack first-param) (nth stack second-param)))
          ))))

(defn compute [noun verb]
    (first (reduce #(operation %1 %2) (instack2 noun verb) (range 0 (count instack) 4))))

(def variable-input 
    (let [execs (for [noun (range 100) verb (range 100)] 
        {:noun noun :verb verb :result (compute noun verb)})
        exec (first (filter #(= 19690720 (:result %)) execs))] 
        (+ (* 100 (:noun exec)) (:verb exec))))

(defn run []
    {:part1 (compute 12 2)
    :part2 variable-input})
      