package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.ResultEntry;
import storages.TopResultStore;

import java.io.IOException;
import java.util.List;

public class UserInfoHandler implements HttpHandler {
    private final TopResultStore store;
    private final ObjectMapper mapper = new ObjectMapper();


    public UserInfoHandler(TopResultStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        if (parts.length != 3) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        int userId = Integer.parseInt(parts[2]);
        List<ResultEntry> results = store.getUserTopResults(userId);

        String response = mapper.writeValueAsString(results);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
