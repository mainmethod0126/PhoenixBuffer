package phoenixbuffer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/* 
 * What is Phoenix? : https://ko.wikipedia.org/wiki/%EB%B6%88%EC%82%AC%EC%A1%B0
 */
public class PhoenixBuffer<T> {

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
    private Consumer<T> ignitionTask;
    private Consumer<T> cleanup;
    private Consumer<T> addFunc;

    public PhoenixBuffer(/*Class<T> bufferClass,*/ Life life, Consumer<T> ignitionTask, Consumer<T> cleanup,
            Consumer<T> add) {
        this.life = life;
        this.ignitionTask = ignitionTask;
        this.cleanup = cleanup;
        this.addFunc = add;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ignitionTask.accept(buffer);
                cleanup.accept(buffer);
            }
        };

        if (this.life != null) {
            Life.getTimer().schedule(task, life.time);
        }
    }

    public synchronized void add() {
        if (addFunc != null) {
            this.addFunc.accept(buffer);
        }
    }

    public synchronized void clean() {
        if (cleanup != null) {
            this.cleanup.accept(buffer);
        }
    }

    public synchronized void ignition() {
        if (cleanup != null) {
            this.ignitionTask.accept(buffer);
        }
    }

    public long getMaxSize() {
        return this.life.size;
    }

    public long getTimeInterval() {
        return this.life.time;
    }
}
