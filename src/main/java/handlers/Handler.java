package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import storages.TopResultStore;

public class Handler {
    protected final TopResultStore store;
    protected final ObjectMapper mapper = new ObjectMapper();

    public Handler(TopResultStore store) {
        this.store = store;
    }
}
