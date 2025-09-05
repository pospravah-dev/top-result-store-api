package storages;

import models.ResultEntry;

import java.util.*;
import java.util.concurrent.*;

public class TopResultStoreInMemory implements TopResultStore {
    private final Map<Integer, NavigableSet<ResultEntry>> userResults = new ConcurrentHashMap<>();
    private final Map<Integer, NavigableSet<ResultEntry>> levelResults = new ConcurrentHashMap<>();

    private final Comparator<ResultEntry> resultComparator = Comparator
            .comparingInt((ResultEntry r) -> -r.getResult())
            .thenComparingInt(r -> r.getLevelId())
            .thenComparingInt(r -> r.getUserId());

    @Override
    public void setResult(int userId, int levelId, int result) {
        ResultEntry entry = new ResultEntry(userId, levelId, result);

        userResults.compute(userId, (k, v) -> updateTopSet(v, entry));
        levelResults.compute(levelId, (k, v) -> updateTopSet(v, entry));
    }

    private NavigableSet<ResultEntry> updateTopSet(NavigableSet<ResultEntry> set, ResultEntry entry) {
        if (set == null) set = new TreeSet<>(resultComparator);
        set.removeIf(e -> e.getUserId() == entry.getUserId() && e.getLevelId() == entry.getLevelId());
        set.add(entry);
        if (set.size() > 20) set.pollLast(); // remove lowest
        return set;
    }

    @Override
    public List<ResultEntry> getUserTopResults(int userId) {
        return new ArrayList<>(userResults.getOrDefault(userId, new TreeSet<>(resultComparator)));
    }

    @Override
    public List<ResultEntry> getLevelTopResults(int levelId) {
        return new ArrayList<>(levelResults.getOrDefault(levelId, new TreeSet<>(resultComparator)));
    }
}