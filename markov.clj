(ns user
    (:require [clojure.contrib.str-utils2 :as str-utils2]))

; get a corpus, turn into list of words, make lowecase
(def corpus
    (->> (slurp "./src/wordplay/sentimental.education.txt")
         (re-seq #"[A-Za-z]{2,}")
         (map str-utils2/lower-case)))

; map of the 2grams keyed by the first word
(def map-of-2grams
    (group-by first (map list corpus (rest corpus))))

; get the second word of a random 2gram beginning with
; the current word.
(defn next-word [current-word]
    (second (rand-nth (map-of-2grams current-word))))

; returns a lazy seq of markov-chained words
(defn mark-chain []
    (iterate next-word (rand-nth corpus)))

; joins a length of markov chain into a space-separated string
(defn make-string [length]
    (str-utils2/join " " (take length (mark-chain))))
