(ns tower.hanoi
    (use clojure.tools.trace))

(defn make-board [size]
    [(range 1 (+ size 1)) () ()])

(defn move-1 [[src dst oth]]
    [(rest src) (conj dst (first src)) oth])

(defn swap [triple [new1st new2nd new3rd]]
    [(triple new1st) (triple new2nd) (triple new3rd)])

(defn move-n [towers n]
    (if (= 1 n)
        (move-1 towers)
        (-> towers
            (swap [0 2 1]) (move-n (dec n)) (swap [0 2 1])
            (move-1)
            (swap [2 1 0]) (move-n (dec n)) (swap [2 1 0]))))

(defn play [size]
    (move-n (make-board size) size))
