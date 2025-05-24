# Java Multithreaded Web Server

This project demonstrates the implementation and benchmarking of a *custom multithreaded web server in Java, enhanced with thread pooling, request monitoring, and a basic token-based authentication layer. The server was tested for performance using **Apache JMeter*, with improvements visualized through detailed test graphs and terminal logs.

---

## Project Structure

webserver/ ├── SingleThreaded/ │   ├── Client.java │   └── Server.java ├── Multithreaded/ │   ├── Client.java │   └── Server.java ├── ThreadPool/ │   ├── Client.java │   └── Server.java

---

## Features

### 1. *Single-Threaded Server*
- Handles one client at a time.
- Simple but blocks on long-running requests.

### 2. *Multithreaded Server*
- Spawns a new thread for each incoming client.
- Improved concurrency but may lead to resource exhaustion under heavy load.

### 3. *Thread Pool-Based Server*
- Uses ExecutorService (FixedThreadPool) for better thread management.
- Prevents system overload by reusing threads.
- Implements metrics tracking and token-based authentication.

---

## Enhancements

### Monitoring & Metrics
- *Request count* tracking using AtomicInteger.
- Timestamped request logging.
- Thread name and client IP logging for each request.

### Security Layer
- *Shared secret token* system.
- Rejects unauthorized clients with HTTP 403 Unauthorized.

---

## Technologies Used

- Java SE (Core Java, Sockets, Concurrency)
- Apache JMeter (for load testing)
- VS Code (IDE)
- Git & GitHub (version control and hosting)

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/java-webserver.git
   cd java-webserver

2. Compile the server and client:

javac ThreadPool/Server.java
javac ThreadPool/Client.java


3. Run the server:

java ThreadPool.Server


4. In a separate terminal, run the client(s):

java ThreadPool.Client




---

Performance Benchmarking

Tested using Apache JMeter with the following configuration:

100 concurrent users

5-minute test duration

Compared latency and throughput across:

Single-threaded

Unbounded multithreaded

Thread pool versions



Screenshots (attached in repo):

Red samples turning green

Performance graphs (latency, throughput)

Terminal logs showing authenticated vs unauthorized requests



---

Key Learnings

Gained deep understanding of Java Socket Programming and multithreading.

Learned to manage concurrency using ExecutorService and AtomicInteger.

Implemented basic security using shared token validation.

Practiced load testing with JMeter and interpreting performance metrics.

Understood the tradeoffs between different threading models in server design.



---

Future Improvements

1. Add file serving and HTTP response formatting.

2. Implement rate limiting or IP blacklisting.

3. Deploy on a local or cloud VM for remote testing.



---

License

This project is open-sourced under the MIT License.


---
