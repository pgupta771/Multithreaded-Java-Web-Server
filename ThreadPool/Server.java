import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private final ExecutorService threadPool;
    private final AtomicInteger requestCount = new AtomicInteger();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        String validToken = "SECRET123"; // Shared token between client and server

        try (
            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String receivedToken = fromSocket.readLine();

            if (receivedToken == null || !receivedToken.equals(validToken)) {
                toSocket.println("403 Unauthorized - Invalid token");
                System.out.println("[SECURITY] Rejected connection from " + clientSocket.getInetAddress());
                clientSocket.close();
                return;
            }

            // Valid token
            requestCount.incrementAndGet();
            System.out.println("[INFO] Authenticated request from: " + clientSocket.getInetAddress()
                    + " | Handled by: " + Thread.currentThread().getName()
                    + " | Time: " + new Date());

            toSocket.println("Hello from authenticated server " + clientSocket.getInetAddress());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);

            // Start metrics logging every 1 second
            server.scheduler.scheduleAtFixedRate(() -> {
                System.out.println("[Metrics] Requests/sec: " + server.requestCount.getAndSet(0));
                if (server.threadPool instanceof ThreadPoolExecutor executor) {
                    System.out.println("[Metrics] Active Threads: " + executor.getActiveCount());
                    System.out.println("[Metrics] Queue Size: " + executor.getQueue().size());
                }
            }, 0, 1, TimeUnit.SECONDS);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            server.scheduler.shutdown();
            server.threadPool.shutdown();
        }
    }
}