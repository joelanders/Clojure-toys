solitaire.chess.clj
===================

    solitaire.chess.clj=> (def joe (random-board 5 4))                                                        │~                                                                                                        
    \#'solitaire.chess.clj/joe                                                                                 │~                                                                                                        
    
    solitaire.chess.clj=> (count-pieces joe)                                                                  │~                                                                                                        
    5                                                                                                         │~                                                                                                        
    
    solitaire.chess.clj=> (print-board joe)                                                                   │~                                                                                                        
    :o :k :q :o                                                                                               │~                                                                                                        
    :o :o :o :o                                                                                               │~                                                                                                        
    :o :o :b :o                                                                                               │~                                                                                                        
    :n :k :o :o                                                                                               │~                                                                                                        
    nil
    
    solitaire.chess.clj=> (solve joe)                                                                         │~                                                                                                        
    1 boards, each with 5 pieces                                                                              │~                                                                                                        
    7 boards, each with 4 pieces                                                                              │~                                                                                                        
    27 boards, each with 3 pieces                                                                             │~                                                                                                        
    34 boards, each with 2 pieces                                                                             │~                                                                                                        
    14 boards, each with 1 pieces                                                                             │~                                                                                                        
    done                                                                                                      │~                                                                                                        
    nil
