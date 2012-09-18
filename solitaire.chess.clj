(ns solitaire.chess.clj)
(use '[clojure.string :only (join)])

; pawn, rook, knight, bishop, queen, king
(def pieces '(:p :r :n :b :q :k))

; Uses :o to mark an empty square.
(defn make-square-board [n]
  (zipmap (for [x (range n), y (range n)] [x y])
          (cycle [:o])))

(defn insert-piece [piece pos board]
  (assoc board pos piece))

; pieces looks like '((:k [0 0]) (:q [0 1]))
(defn populate-board [pieces board]
  (if (empty? pieces)
    board
    (let [sym (first  (first pieces)),
          pos (second (first pieces))]
      (populate-board (rest  pieces)
        (insert-piece sym pos board)))))

; This doesn't check if two pieces
; end up on the same square.
(defn random-pieces [num-pieces board-size]
  (if (= 0 num-pieces)
    '()
    (cons (list (rand-nth pieces)
                  [ (rand-int board-size)
                    (rand-int board-size) ])
            (random-pieces (dec num-pieces)
                           board-size))))

(defn random-board [num-pieces board-size]
  (populate-board
    (random-pieces num-pieces board-size)
    (make-square-board board-size)))

; Returns the pieces list, eg. '((:k [0 0]) (:q [0 1]))
(defn pieces-on-board [board]
  (let [size (+ 1 (apply max (flatten (keys board))))]
    (remove nil?
      (for [x (range size), y (range size)]
        (if (not= (board [x y]) :o)
          (list (board [x y]) [x y])
          nil)))))

; eg. :o :k
;     :q :o
(defn print-board [board]
  (let [size (+ 1 (apply max (flatten (keys board))))]
    (print
      (join "\n"
        (for [y (range size)]
          (join " "
            (map #(board [% y])
                 (range size))))))))

(defn knight-moves [[x y] board]
  (remove nil?
    (map #(if (board %) % nil)
      (list
        [(+ x 2) (+ y 1)]
        [(+ x 2) (- y 1)]
        [(+ x 1) (+ y 2)]
        [(+ x 1) (- y 2)]
        [(- x 2) (+ y 1)]
        [(- x 2) (- y 1)]
        [(- x 1) (+ y 2)]
        [(- x 1) (- y 2)]))))

(defn king-moves [[x y] board]
  (remove nil?
    (map #(if (board %) % nil)
      (for [dx [-1 0 1], dy [-1 0 1] :when (not (== dx dy 0))]
        [(+ x dx) (+ y dy)]))))

; Only the forward-diagonal attacking moves
(defn pawn-moves [[x y] board]
  (remove nil?
    (map #(if (board %) % nil)
      (list
        [(+ x 1) (+ y 1)]
        [(- x 1) (+ y 1)]))))

(defn rook-moves [[x y] board]
  (let [size (+ 1 (apply max (flatten (keys board))))]
    (remove nil?
      (map #(if (board %) % nil)
        (concat
          (for [dx (range (- 0 size) size) :when (not= dx 0)]
            [(+ dx x) y])
          (for [dy (range (- 0 size) size) :when (not= dy 0)]
            [x (+ dy y)]))))))

(defn bishop-moves [[x y] board]
  (let [size (+ 1 (apply max (flatten (keys board))))]
    (remove nil?
      (map #(if (board %) % nil)
        (for [dx (range (- 0 size) size),
              dy [(- 0 dx) dx] :when (not= dx dy 0)]
          [(+ dx x) (+ dy y)])))))

(defn queen-moves [[x y] board]
  (concat (rook-moves   [x y] board)
          (bishop-moves [x y] board)))

