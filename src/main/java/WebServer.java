import com.sun.net.httpserver.HttpServer;
import handlers.LevelInfoHandler;
import handlers.SetInfoHandler;
import handlers.UserInfoHandler;
import storages.TopResultStore;
import storages.TopResultStoreInMemory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {

    private final int port;
    private HttpServer server;
    private final TopResultStore store = new TopResultStoreInMemory();

    public WebServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.createContext("/userinfo", new UserInfoHandler(store));
        server.createContext("/levelinfo", new LevelInfoHandler(store));
        server.createContext("/setinfo", new SetInfoHandler(store));

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }
}
