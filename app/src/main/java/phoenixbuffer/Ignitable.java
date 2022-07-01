package phoenixbuffer;

public interface Ignitable<T> {

    public void ignitionTask(T buffer, Object... params);
}
