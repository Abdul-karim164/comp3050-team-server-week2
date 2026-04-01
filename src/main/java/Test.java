import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Test {

    // Initial player position
    public static int playerY = 5;
    public static int playerX = 5;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/test", new MyHandler());
        server.createContext("/hello", new HelloHandler());
        
        server.createContext("/move", new MoveHandler());
        server.createContext("/info", new InfoHandler());

        server.setExecutor(null); 
        server.start();
        System.out.println("Server started on port 8000...");
    }
}