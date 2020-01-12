# Assumption:
For statistics calculations these are my assumptions
  + To keep it simple I just used a normal, every time statistics processing. Which is not optimized yet delivers to purpose to show my abilities.
  + Another approach O(1) is using *MOVING WINDOW* or *MOVING SUM* algorithm:
    - Have a sorted list of transactions by time
    - On add → add (+ operation) this new transaction to existing statistics
    - On timeout → remove timed-out (- operation) transaction in statistics.
    
    But this approach need at least 2 days to implement successfully. Which I didn't had.
