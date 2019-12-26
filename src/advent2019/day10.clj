(ns advent2019.day10
  (:require [advent2019.utils :as utils]
            [clojure.string :as s]
            [clojure.test :as t]))

(def INPUT (utils/day-file 10))

(defn inp->grid [str] (s/split-lines str))

(defn hasAsteroid [str] (= str \#))


(map #(assoc % :y 1) (map-indexed (fn [idx item] {:x idx :hasAsteroid (hasAsteroid item)}) "......#.#."))

(defn solve-1 [str] str)

(t/deftest test1 [] (t/is (= 8 (solve-1 ".#..#
.....
#####
....#
...##")) ))

(t/deftest test2 [] (t/is (= 33 (solve-1 "......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####"))))

(t/deftest test3 [] (t/is (= 33 (solve-1 ".#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#.."))))

