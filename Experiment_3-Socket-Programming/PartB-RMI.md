In Socket programming → we manually send/receive data.\
In RMI → we call methods directly. No manual data transmission code needed.

<br>


| Component                 | File              | Purpose                                       |
| ------------------------- | ----------------- | --------------------------------------------- |
| **Remote Interface**      | `AddI.java`       | Defines the method available for remote call  |
| **Server Implementation** | `AddServer.java`  | Implements the remote interface               |
| **Server Registration**   | `RegisterMe.java` | Registers remote object into RMI registry     |
| **Client Program**        | `AddClient.java`  | Looks up remote object and invokes its method |

<br>

```
Interface (make blueprint🟦) ->  Server (Implement Blueprint) -> Server (Register in Registry)📃
                                                                                            ^
                                                                                            |
                                                                                        Client (Lookup🔍 In
                                                                                         registry and
                                                                                        Invoke Method)
```
