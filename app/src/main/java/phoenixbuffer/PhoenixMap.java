package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PhoenixMap<K, V> {

    private PhoenixBuffer<Map<K, V>> phoenixBuffer;

    private Ignitable ignitable;
    private Map<K, V> buffer;

    public PhoenixMap(long size, long time, Ignitable<Map<K, V>> ignitable)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {

        this.ignitable = ignitable;

        new PhoenixBuffer.Life(size, time);
        phoenixBuffer = new PhoenixBuffer<>(
                buffer = new HashMap<>(),
                new PhoenixBuffer.Life(
                        size, time),
                new PhoenixBuffer.Funtions<Map<K, V>>() {
                    @Override
                    public void ignitionTask(Map<K, V> buffer, Object... params) {
                        ignitable.ignitionTask(buffer, params);
                    }

                    @Override
                    public void add(Map<K, V> buffer, Object... params) {
                        if (params[0] != null) {
                            K key = (K) params[0];
                            if (params[1] != null) {
                                V value = (V) params[1];
                                buffer.put(key, value);
                            }
                        }
                    }

                    @Override
                    public void clean(Map<K, V> buffer) {
                        buffer.clear();
                    }
                });
    }

    public synchronized void add(K key, V value) {
        phoenixBuffer.add(key, value);
    }

    public synchronized void clean() {
        phoenixBuffer.clean();
    }
}
