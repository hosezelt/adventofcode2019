(ns advent2019.testing
  (:require [advent2019.utils :as utils]
            [advent2019.day5 :as day5]
            [clojure.string :as s]
            [clojure.test :refer :all]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]
            [clojure.math.combinatorics :as combo]))

(defn train
  []
  (println "choo choo!"))

(def hello "hello")
