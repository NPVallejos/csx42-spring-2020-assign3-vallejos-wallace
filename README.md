# CSX42: Assignment 3
## Name: Nicholas Vallejos and Kevin Wallace

-----------------------------------------------------------------------
-----------------------------------------------------------------------


## Instruction to clean:

```commandline
ant -buildfile numberPlay/src/build.xml clean
```

-----------------------------------------------------------------------
## Instruction to compile:

```commandline
ant -buildfile numberPlay/src/build.xml all
```

-----------------------------------------------------------------------
## Instruction to run:

#### Note: Run the persister service before the prime detector

#### Use the below command to run the persister service.

```commandline
ant -buildfile numberPlay/src/build.xml run-persister-service \
-Dport="<Port number on which the server should listen>" \
-DoutputFile="<Name of the output file to which the data received on the port should be written>"
```

#### Use the below command to run the prime detector.

```commandline
ant -buildfile numberPlay/src/build.xml run-prime-detector \
-DinputFile="<Input file path>" \
-DnumThreads="<The number of threads to be used>" \
-Dcapacity="<Capacity of the results data structure>" \
-DpersisterServiceIp="<IP Address of the PersisterService (Note that the PersisterService should be started before PrimeDetector)>" \
-DpersisterServicePort="<Port number on which the PersisterService is listening for data>" \
-DdebugValue="<An integer that controls what is printed on stdout>"
```

-----------------------------------------------------------------------
## References:
- Idea for checking if a number is prime: https://en.wikipedia.org/wiki/Primality_test#Simple_methods
- Idea for synchronized blocks: https://www.geeksforgeeks.org/inter-thread-communication-java/

-----------------------------------------------------------------------
## Important Notes:
- I used the FileProcessor.java class provided by the last assignment.
- The choice of data structure for the Results class was a Vector for several reasons.
	- The Vector is synchronized
	- It has constant insertion time (O(1))
	- It has constant look-up time (O(1))
	- In terms of space complexity, it should be O(n). For this assignment, the vector will never double in size because we do not go past its max capacity. Thus, worst-case space complexity should be O(capacity), where capacity refers to the command arg input 'capacity'.
- The connection made beteen sockets is done using the localHostAddress

-----------------------------------------------------------------------
## We used 1 slack day
