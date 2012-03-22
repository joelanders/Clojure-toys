(ns word.play
    (:require [clojure.contrib.str-utils2 :as str-utils2]))

; get a set of english words
(def dict
    (->> (slurp "/usr/share/dict/words")
         str-utils2/split-lines
         (map str-utils2/lower-case)
         (into #{})))

; sort the letters in a string.
(defn sort-string [input]
    (apply str (sort input)))

; put the words into a map keyed by each word's
; sorted letters.
(def anamap (group-by sort-string dict))

; returns all of the anagrams of a word
(defn find-anas [word]
    (anamap (sort-string word)))

; size of each anagram set
(def anacounts (for [[k ws] anamap] (count ws)))

; frequencies of anagram set sizes.
(frequencies anacounts)

; "how many words have x anagrams?"
(into {} (map (fn [[set-size freq]] {set-size (* set-size freq)})
     (frequencies anacounts)))

; number of words with at least one anagram
(apply + (filter #(>= % 2) anacounts))
