package phoenixbuffer.interfaces;

@FunctionalInterface
public interface Cleanable<T> {

    public void clean(T buffer);
}
