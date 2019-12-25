(ns advent2019.day5
    (:require [clojure.string :as s])
    (:require [advent2019.utils :as utils]))

(def INPUT (utils/day-file 5))

(defn split-input [input]
    (s/split input #","))

(defn int-codes [string]
    (->> string
        split-input
        (map read-string)
        (into [])))

(defn parse-opcode [code]
    (let [x (format "%02d" code)]
      [(subs x (- (count x) 2))
       (concat (map #(Integer/parseInt (str %)) (drop 2 (reverse x)))
               (repeat 0))]))

(defn operation [{:keys [program pointer in-f out-f relative-base] :as m}]
  (let [[opcode & parameters] (drop pointer program)
        [instruction modes] (parse-opcode opcode)
        mode->adr (fn [pos]
                    (let [param (nth parameters pos)
                          mode (nth modes pos)]
                      (case mode
                        0 param
                        1 param
                        2 (+ param relative-base))))
        mode->value (fn [pos]
                    (let [param (nth parameters pos)
                          mode (nth modes pos)]
                      (case mode
                        0 (nth program param)
                        1 param
                        2 (nth program (+ param relative-base)))))]
    (condp = instruction
            ;; Halt
      "99"  (assoc m :stop true)

            ;; ADD
      "01" (-> (assoc-in m [:program (mode->adr 2)] (+
                                                          (mode->value 0)
                                                          (mode->value 1)))
               (update :pointer + 4))
            ;; Multiply
      "02" (-> (assoc-in m [:program (mode->adr 2)] (*
                                                          (mode->value 0)
                                                          (mode->value 1)))
               (update :pointer + 4))
            ;; Store Input
      "03" (-> (assoc-in m [:program (mode->adr 0)] (in-f))
               (update :pointer + 2))

            ;; Set Output
      "04" (do (out-f (mode->value 0))
               (update m :pointer + 2))

            ;; Jump if true
      "05" (if (not= 0 (mode->value 0))
             (assoc m :pointer (mode->value 1))
             (update m :pointer + 3))

            ;; Jump if false
      "06" (if (= 0 (mode->value 0))
             (assoc m :pointer (mode->value 1))
             (update m :pointer + 3))

            ;; Less Than
      "07" (-> (assoc-in m [:program (mode->adr 2)]
                         (if (< (mode->value 0) (mode->value 1))
                           1 0))
               (update :pointer + 4))

            ;; Equal
      "08" (-> (assoc-in m [:program (mode->adr 2)]
                         (if (= (mode->value 0) (mode->value 1))
                           1 0))
               (update :pointer + 4))

            ;; Adjust Relative-base
      "09" (-> (update m :relative-base + (mode->value 0))
               (update :pointer + 2)))))

(defn compute [{:keys [stop] :as m}]
  (if-not stop (recur (operation m)) m))

(defn solve-1 []
  (let [input (int-codes INPUT)
        in    (atom [5])
        out   (atom [])
        in-f  (fn [] (first @in))
        out-f #(swap! out conj %)]
    (compute {:program input :in-f in-f :out-f out-f :pointer 0})
    (->> @out
         last)))

(defn solve-2 []
  (let [input (int-codes INPUT)
        in    (atom [5])
        out   (atom [])
        in-f  (fn [] (first @in))
        out-f #(swap! out conj %)]
    (compute {:program input :in-f in-f :out-f out-f :pointer 0})
    (->> @out
         last)))
