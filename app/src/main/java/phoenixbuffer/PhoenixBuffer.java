package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

/* 
 * What is Phoenix? : https://ko.wikipedia.org/wiki/%EB%B6%88%EC%82%AC%EC%A1%B0
 */
public class PhoenixBuffer<T> {

    public static interface Funtions<T> extends Ignitable<T>, Addable<T>, Cleanable<T> {
    }

    public static class Life {
        private long size;
        private long time;
        private static Timer timer;

        public Life(long size, long time) {
            this.size = size;
            this.time = time;
        }

        /*
         * Timer는 전역객체로 하나만 생성하여 범용으로 사용합니다.
         */
        public static Timer getTimer() {
            if (timer == null) {
                timer = new Timer("Life Timer");
            }
            return timer;
        }
    }

    private Life life;
    private T buffer;
    private Funtions<T> functions;
    private TimerTask task;

    public PhoenixBuffer(T buffer, Life life, Funtions<T> functions)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        this.life = life;
        this.functions = functions;
        this.buffer = buffer;

        this.task = new TimerTask() {
            @Override
            public void run() {
                functions.ignitionTask(buffer);
                functions.clean(buffer);
            }
        };

        if (this.life != null) {
            Life.getTimer().schedule(this.task, 1, life.time);
        }
    }

    public synchronized void add(Object... params) {
        functions.add(buffer, params);
    }

    public synchronized void clean() {
        functions.clean(buffer);
    }

    public long getMaxSize() {
        return this.life.size;
    }

    public long getTimeInterval() {
        return this.life.time;
    }

    public T getBuffer() {
        return this.buffer;
    }
}
