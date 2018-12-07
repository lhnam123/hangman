package com.hangman.game.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameOutput {

    private String status;

    private JsonNode data;

    public GameOutput(final String status, final Object data) {

        ObjectMapper mapper = new ObjectMapper();
        this.status = status;
        this.data = mapper.convertValue(data, JsonNode.class);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(final Object data) {
        ObjectMapper mapper = new ObjectMapper();
        this.data = mapper.convertValue(data, JsonNode.class);
    }
}
