(ns advent2019.day7
  (:require [clojure.core.async :as a]
            [clojure.math.combinatorics :as combo]
            [advent2019.day5 :as day5]
            [advent2019.utils :as utils]))

(def INPUT (utils/day-file 7))

(defn system-1 [input phases]
  (let [[a b c d e :as channels] (repeatedly #(a/chan))
        out                      (a/chan)]
    (a/go
      (dorun (map (fn [channel phase] (a/>!! channel phase)) channels phases))
      (a/>!! a 0))
    (dorun
     (map (fn [in-chan out-chan]
            (a/go (day5/compute
                   {:program input :in-f #(a/<!! in-chan) :out-f #(a/>!! out-chan %) :pointer 0})
                  (a/close! in-chan)
                  (a/close! out-chan)))
          [a b c d e]
          [b c d e out]))
    (->> (repeatedly #(a/<!! out))
         (take-while identity)
         last)))

(defn solve-1 []
  (let [input (day5/int-codes INPUT)]
    (->> (combo/permutations [0 1 2 3 4])
         (map #(system-1 input %))
         (sort >)
         first)))

(defn system-2 [input phases]
  (let [[a b c d e :as channels] (repeatedly #(a/chan))
        out                      (a/chan)]
    (a/go
      (dorun (map (fn [channel phase] (a/>!! channel phase)) channels phases))
      (a/>!! a 0))
    (dorun
     (map (fn [in-chan out-chan]
            (a/go (day5/compute
                   {:program input :in-f #(a/<!! in-chan) :out-f #(a/>!! out-chan %) :pointer 0})
                  (a/close! in-chan)
                  (a/close! out-chan)))
          [a b c d e]
          [b c d e out]))
    (->> (repeatedly #(a/<!! out))
         (take-while identity)
         last)))

(defn solve-1 []
  (let [input (day5/int-codes INPUT)]
    (->> (combo/permutations [9 8 7 6 5])
         (map #(system-1 input %))
         (sort >)
         first)))