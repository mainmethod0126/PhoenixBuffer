package phoenixbuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import phoenixbuffer.interfaces.Addable;
import phoenixbuffer.interfaces.Cleanable;
import phoenixbuffer.interfaces.FullCheck;
import phoenixbuffer.interfaces.Ignitable;

/* 
 * What is Phoenix? : https://ko.wikipedia.org/wiki/%EB%B6%88%EC%82%AC%EC%A1%B0
 */
public class PhoenixBuffer<T> {

    public static interface Funtions<T> extends Ignitable<T>, Addable<T>, Cleanable<T>, FullCheck<T> {
    }

    public static class Life {
        private long size;
        private long time;
        private Timer timer;

        public Life(long size, long time) {
            this.size = size;
            this.time = time;
            this.timer = new Timer("Life Timer");
        }

        public long getSize() {
            return this.size;
        }

        public long getTime() {
            return this.time;
        }

        public Timer getTimer() {
            return timer;
        }
    }

    private Life life;
    private T buffer;
    private Funtions<T> functions;
    private TimerTask lifeTimeOverTask;
    private Object lock;


    public PhoenixBuffer(T buffer, Life life, Funtions<T> functions)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        this.life = life;
        this.functions = functions;
        this.buffer = buffer;
        this.lock = new Object();

        this.lifeTimeOverTask = new TimerTask() {
            @Override
            public void run() {
                basicFeaturesOfThePhoenix();
            }
        };

        if (this.life != null) {
            life.getTimer().scheduleAtFixedRate(this.lifeTimeOverTask, 0, life.time);
        }
    }

    private void basicFeaturesOfThePhoenix() {
        synchronized(this.lock){
            this.ignitionTask();
            this.clean();
        }
    }

    public boolean isFull() {
        synchronized (this.lock) {
            return functions.isFull(buffer);
        }
    }

    public void ignitionTask() {
        synchronized (this.lock) {
            functions.ignitionTask(buffer);
        }
    }

    public void add(Object... params) {
        synchronized (this.lock) {
            functions.add(buffer, params);
        }
    }

    public void clean() {
        synchronized (this.lock) {
            functions.clean(buffer);
        }
    }

    public long getMaxSize() {
        return this.life.size;
    }

    public long getTimeInterval() {
        return this.life.time;
    }

    public Life getLife() {
        return this.life;
    }

    public synchronized void cancel() {
        lifeTimeOverTask.cancel();
    }
}
