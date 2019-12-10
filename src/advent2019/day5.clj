(ns advent2019.day5
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(def INPUT (utils/day-file 5))

(defn split-input [input]
    (s/split input #","))

(defn int-codes []
    (->> INPUT
        split-input
        (map read-string)
        (into [])))

(def in-out (atom 0))

(defn parse-opcode [code]
    (let [x (format "%02d" code)]
      [(subs x (- (count x) 2))
       (concat (map #(Integer/parseInt (str %)) (drop 2 (reverse x)))
               (repeat 0))]))

(defn operation [program pointer]
    (let [[opcode & parameters] (drop pointer program)
         [instruction modes] (parse-opcode opcode)
         get-value (fn [pos]
            (let [param (nth parameters pos)
                  mode (nth modes pos)]
                  (if (= mode 0) (nth program param) 
                                 param)))]
        (condp = instruction
            ;; ADD
            "01" [(assoc program (nth parameters 2) (+
                                                        (get-value 0)
                                                        (get-value 1))) 
                     (+ pointer 4)]
            ;; Multiply
            "02" [(assoc program (nth parameters 2) (* 
                                                        (get-value 0)
                                                        (get-value 1))) 
                     (+ pointer 4)]
            ;; Store Input
            "03" [(assoc program (nth parameters 0) @in-out) 
                     (+ pointer 2)]

            ;; Set Output
            "04" [(do (reset! in-out (get-value 0)) program) 
                     (+ pointer 2)]

            ;; Jump if true
            "05" [program 
                   (if (not= 0 (get-value 0)) 
                     (get-value 1) 
                     (+ pointer 3))]

            ;; Jump if false
            "06" [program 
                    (if (= 0 (get-value 0))
                     (get-value 1) 
                     (+ pointer 3))]

            ;; Less Than
            "07" [(assoc program (nth parameters 2) 
                    (if (< (get-value 0) (get-value 1)) 
                            1 0)) 
                     (+ pointer 4)]

            ;; Equal
            "08" [(assoc program (nth parameters 2) 
                    (if (= (get-value 0) (get-value 1))
                            1 0)) 
                     (+ pointer 4)]
            )))

(defn compute [program input]
    (reset! in-out input)
    (loop [program program
           pointer 0]
        (if (= (nth program pointer) 99)
          @in-out
          (let [[program pos] (operation program pointer)]
            (recur program pos)))))

