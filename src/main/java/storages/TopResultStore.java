package storages;

import models.ResultEntry;

import java.util.List;

public interface TopResultStore {
    void setResult(int userId, int levelId, int result);

    List<ResultEntry> getUserTopResults(int userId);

    List<ResultEntry> getLevelTopResults(int levelId);
}
