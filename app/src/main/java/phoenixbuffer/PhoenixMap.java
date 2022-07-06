package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import phoenixbuffer.interfaces.Ignitable;

public class PhoenixMap<K, V> implements Map<K,V>{

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

    public void cancel() {
        phoenixBuffer.cancel();
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public V get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (phoenixBuffer.isFull()) {
            phoenixBuffer.ignitionTask();
        }

        phoenixBuffer.add(key, value);

        return value;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public V remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub

    }

    
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        phoenixBuffer.clean();

    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * It is not implemented and cannot be used.
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }
}
