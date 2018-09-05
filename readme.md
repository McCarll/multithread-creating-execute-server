Incoming params (LocalDateTime, Callable)
Task:
 - many threads pushing data to server (queue)
 - another thread execute Callable after filtering by LocalDateTime if server not overload. 
- if system overburdened - ignore filtering by LocalDateTime
