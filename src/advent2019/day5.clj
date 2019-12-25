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

(defn operation [{:keys [program pointer in-f out-f] :as m}]
    (let [[opcode & parameters] (drop pointer program)
          [instruction modes] (parse-opcode opcode)
          get-value (fn [pos]
                      (let [param (nth parameters pos)
                            mode (nth modes pos)]
                        (if (= mode 0) (nth program param) 
                            param)))]
      (condp = instruction
            ;; Halt
        "99"  (assoc m :stop true)

            ;; ADD
        "01" (-> (assoc-in m [:program (nth parameters 2)] (+
                                                            (get-value 0)
                                                            (get-value 1)))
                 (update :pointer + 4))
            ;; Multiply
        "02" (-> (assoc-in m [:program (nth parameters 2)] (*
                                                            (get-value 0)
                                                            (get-value 1)))
                 (update :pointer + 4))
            ;; Store Input
        "03" (-> (assoc-in m [:program (nth parameters 0)] (in-f))
                 (update :pointer + 2))

            ;; Set Output
        "04" (do (out-f (get-value 0))
                 (update m :pointer + 2))

            ;; Jump if true
        "05" (if (not= 0 (get-value 0))
               (assoc m :pointer (get-value 1))
               (update m :pointer + 3))

            ;; Jump if false
        "06" (if (= 0 (get-value 0))
               (assoc m :pointer (get-value 1))
               (update m :pointer + 3))

            ;; Less Than
        "07" (-> (assoc-in m [:program (nth parameters 2)]
                           (if (< (get-value 0) (get-value 1))
                             1 0))
                 (update :pointer + 4))

            ;; Equal
        "08" (-> (assoc-in m [:program (nth parameters 2)]
                           (if (= (get-value 0) (get-value 1))
                             1 0))
                 (update :pointer + 4)))))

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
