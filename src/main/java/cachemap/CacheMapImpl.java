package cachemap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CacheMapImpl<K, T> implements CacheMap<K, T> {
    private long timeToLive;
    private Map<K, T> cacheMap;
    private Map<K, Long> timeMap;

    public CacheMapImpl() {
        timeToLive = 0;
        cacheMap = new HashMap<K, T>();
        timeMap = new HashMap<K, Long>();
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public T put(K key, T value) {
        clearExpired();
        timeMap.put(key, Clock.getTime());
        return cacheMap.put(key, value);
    }

    public void clearExpired() {
        if (timeToLive > 0) {
            for (Iterator<Map.Entry<K, Long>> it = timeMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry e = it.next();
                if ((Clock.getTime() - (Long) e.getValue()) >= timeToLive) {
                    cacheMap.remove(e.getKey());
                    it.remove();
                }
            }
        }
    }

    public void clear() {
        cacheMap.clear();
        timeMap.clear();
    }

    public boolean containsKey(Object key) {
        clearExpired();
        return cacheMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        clearExpired();
        return cacheMap.containsValue(value);
    }

    public T get(Object key) {
        clearExpired();
        return cacheMap.get(key);
    }

    public boolean isEmpty() {
        clearExpired();
        return cacheMap.isEmpty();
    }

    public T remove(Object key) {
        clearExpired();
        return cacheMap.remove(key);
    }

    public int size() {
        clearExpired();
        return cacheMap.size();
    }
}
