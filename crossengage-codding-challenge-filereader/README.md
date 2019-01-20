**Crossengage Coding Challenge** 

**File Reader Module:**
File Reader Module is responsible for:  
- Reading file line by line.
- Produces messages and deliver them to next module via flink producer for parsing.

 
```
+-------------+     +-------------+     +-------------+  
|             |     |             |     |             |=>SMS
|             |     |             |     |   MESSAGE   |
| FILEREADER  | ==> | LINEPARSER  | ==> |   SENDER    |=>EMAIL
|             |     |             |     |             |
|      X      |	    |             |     |             |=>ALL
+-------------+     +-------------+     +-------------+
 File -> String      String -> User     User -> Message
        |             |        |           |
   t:f_1000(P)  t:f_1000(C) t:1001(P)   t:1001(C
        |             |        |           |
        +---------------------------------------+  
        |                                       |
        |                FLINK                  |
        |                                       |
        +---------------------------------------+
                           |
                     localhost:9092
                           |
             +-------------------------------+  
             |                               |
             |             KAFKA             |
             |                               |
             +-------------------------------+
         
         
```