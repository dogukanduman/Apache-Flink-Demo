**Crossengage Coding Challenge** 

**Sender Module:**
Sender Module is responsible for:  
- Consuming users from Parser Module
- Splitting DataStream into three SMS,EMAILS or ALL keywords 
- Sending SMS and EMAIL to users who want to notify
 
```
+-------------+     +-------------+     +-------------+  
|             |     |             |     |             |=>SMS
|             |     |             |     |   MESSAGE   |
| FILEREADER  | ==> | LINEPARSER  | ==> |   SENDER    |=>EMAIL
|             |     |             |     |             |
|             |	    |             |     |      X      |=>ALL
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