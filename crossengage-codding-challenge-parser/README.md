**Crossengage Coding Challenge** 

**Parser Module:**
Parser Module is responsible for:  
- Consuming message from FileReader Module
- Mapping Strings into User Class.
- Filtering users who don't want to notify or there is no enough information to reach them.
- Produce messages which contain user info for Message Sender Module.
 
```
P:Producer
C:Consumer
+-------------+     +-------------+     +-------------+  
|             |     |             |     |             |=>SMS
|             |     |             |     |   MESSAGE   |
| FILEREADER  | ==> | LINEPARSER  | ==> |   SENDER    |=>EMAIL
|             |     |             |     |             |
|             |	    |      X      |     |             |=>ALL
+-------------+     +-------------+     +-------------+
 File -> String      String -> User     User -> Message
        |             |        |           |
   t:f_1000(P)  t:f_1000(C) t:1001(P)   t:1001(C)
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
