(ns tower.hanoi
    (use clojure.tools.trace))

(defn make-board [size]
    [(range 1 (+ size 1)) () ()])

(defn move-1 [[src dst oth]]
    [(rest src) (conj dst (first src)) oth])

(defn move-n [[src dst oth] n]
    (if (= 1 n)
       (move-1 [src dst oth])
       (let [[a c b] (move-n [src oth dst] (dec n))
             [d e f] (move-1 [a b c])
             [z y x] (move-n [f e d] (dec n))]
           [x y z])))

(defn play [size]
    (move-n (make-board size) size))
