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

; all the combinations of a collection.
; e.g. (1 2 3) -> #{(1 2 3) (2 3) (1 3) (1 2) (1) (2) (3)}
(defn combinations [things]
    (into #{}
        (if (= 1 (count things))
            (list things)
            (conj (mapcat combinations
                    (for [x things]
                         (remove #(= x %) things)))
                  (seq things)))))

; all the string combinations of a string
; e.g "joe" -> ("joe" "oe" "je" "jo" "j" "o" "e")
(defn str-combs [letters]
    (map #(apply str %) (combinations letters)))

; all the words (of min-length) that can be spelled
; with the given letters.
(defn spell-with [letters min-length]
    (remove #(or (nil? %) (< (count %) min-length))
            (mapcat find-anas (str-combs letters))))
