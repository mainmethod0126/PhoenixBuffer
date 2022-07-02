package phoenixbuffer.interfaces;

public interface Ignitable<T> {

    public void ignitionTask(T buffer, Object... params);
}
