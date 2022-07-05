package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import phoenixbuffer.interfaces.Ignitable;

public class PhoenixMap<K, V> {

    private PhoenixBuffer<Map<K, V>> phoenixBuffer;

    private Ignitable<Map<K, V>> ignitable;
    private Map<K, V> buffer;

    @SuppressWarnings("unchecked")
    public PhoenixMap(long size, long time, Ignitable<Map<K, V>> ignitable)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {

        this.ignitable = ignitable;
        this.buffer = new HashMap<>();

        new PhoenixBuffer.Life(size, time);
        phoenixBuffer = new PhoenixBuffer<>(
                buffer,
                new PhoenixBuffer.Life(
                        size, time),
                new PhoenixBuffer.Funtions<Map<K, V>>() {
                    @Override
                    public synchronized void ignitionTask(Map<K, V> buffer) {
                        ignitable.ignitionTask(buffer);
                    }

                    @Override
                    public synchronized void add(Map<K, V> buffer, Object... params) {
                        if (params[0] != null) {
                            K key = (K) params[0];
                            if (params[1] != null) {
                                V value = (V) params[1];
                                buffer.put(key, value);
                            }
                        }
                    }

                    @Override
                    public synchronized void clean(Map<K, V> buffer) {
                        buffer.clear();
                    }

                    @Override
                    public boolean isFull(Map<K, V> buffer) {
                        return buffer.size() >= size;
                    }
                });
    }

    public void put(K key, V value) {
        if (phoenixBuffer.isFull()) {
            phoenixBuffer.ignitionTask();
        }
        
        phoenixBuffer.add(key, value);
    }

    public void clean() {
        phoenixBuffer.clean();
    }

    public void cancel() {
        phoenixBuffer.cancel();
    }
}
