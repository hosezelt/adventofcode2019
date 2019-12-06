(ns advent2019.day2
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(defn split-input [input]
    (s/split input #","))

(def instack (assoc (assoc (into [] (map read-string (split-input (utils/day-file 2)))) 1 12) 2 2))

(defn operation [count stack]
(let [instr-set (take 4 (drop (* count 4) stack))]
    (let [[opcode 
           first-param
           second-param
           loc] instr-set]
    (cond (= 99 opcode) (println (nth stack 0))
          (= 1 opcode) (assoc stack loc (+ (nth stack first-param) (nth stack second-param)))
          (= 2 opcode) (assoc stack loc (* (nth stack first-param) (nth stack second-param)))
          ))))

(defn compute []
    (loop [x 0 stack instack]
       (when (not= (first stack) 99)
       (recur (inc x) (operation x stack)))))
      