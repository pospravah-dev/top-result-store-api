package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultEntry {
    private final int userId;
    private final int levelId;
    private final int result;

    public ResultEntry(int userId, int levelId, int result) {
        this.userId = userId;
        this.levelId = levelId;
        this.result = result;
    }

    @JsonProperty("user_id")
    public int getUserId() {
        return userId;
    }

    @JsonProperty("level_id")
    public int getLevelId() {
        return levelId;
    }

    @JsonProperty("result")
    public int getResult() {
        return result;
    }
}