package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storages.TopResultStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SetInfoHandler extends Handler  implements HttpHandler {

    public SetInfoHandler(TopResultStore store) {
        super(store);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("put")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream body = exchange.getRequestBody();
        Map<String, Integer> data = mapper.readValue(body, Map.class);

        int userId = data.get("user_id");
        int levelId = data.get("level_id");
        int result = data.get("result");

        store.setResult(userId, levelId, result);
        exchange.sendResponseHeaders(200, -1);
    }
}

