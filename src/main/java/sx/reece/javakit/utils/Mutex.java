package sx.reece.javakit.utils;

import sx.reece.javakit.logger.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by RSX on 22/10/2016.
 */
public class Mutex {
    private final StampedLock _mutex = new StampedLock();
	private long stamp;
    public void runSafely(Runnable runnable) {
        lock();
        runnable.run();
		unlock();
    }

    @Deprecated
    public void tryLock(){
        lock();
    }

    public void lockPerformance() {
        lock();
    }

    public void lock() {
		stamp = _mutex.writeLock();
    }

    public void unlock() {
		_mutex.unlock(stamp);
    }
}
