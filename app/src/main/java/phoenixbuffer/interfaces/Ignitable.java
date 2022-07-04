package phoenixbuffer.interfaces;

@FunctionalInterface
public interface Ignitable<T> {

    public void ignitionTask(T buffer);
}
