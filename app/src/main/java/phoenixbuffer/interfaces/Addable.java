package phoenixbuffer.interfaces;


@FunctionalInterface
public interface Addable<T> {

    public void add(T buffer, Object... params);
}
