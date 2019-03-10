package sx.reece.javakit.utils;

import java.util.Optional;

/**
 * Warning: soon to be deprecated excluding safeSleep!
 */
public class GeneralUtils {
	
    public static void safeSleep(long intervals) {
        try {
            Thread.sleep(intervals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public static void safeYield() {
        try {
            Thread.yield();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getTime() {
        return System.nanoTime() / 1000000;
    }

    /**
     * Do not use this everywhere. if null ? x : is faster and recommended.
     * @param a a potential null object
     * @param c a potential null object
     * @return Best of both
     */
    public static Object getBestObject(Object a, Object c) {
        return a == null ? c : a;
    }

    /**
     * Do not use this everywhere. if null ? x : is faster and recommended.
     * @param a a potential null object
     * @param b a potential null object
     * @return Best of both
     */
    public static Object getBestObject(Optional<?> a, Optional<?> b) {
        return a.isPresent() ? a.get() : b.isPresent() ? b.get() : null;
    }

    /**
     * Do not use this everywhere. if null ? x : is faster and recommended.
     * @param a a potential null object
     * @param b a potential null object
     * @return Best of both
     */
    public static Object getBestObject(Optional<?> a, Object b) {
        return a.isPresent() ? a.get() : b;
    }

    @Deprecated
    public static void count(int finish, Iteration clazz) {
        count(0, finish, clazz);
    }

    @Deprecated
    public static void count(int start, int finish, Iteration clazz) {
        for (int i = start; i < finish; i++) {
            clazz.run(i);
        }
    }

    @Deprecated
    public interface Iteration {
        void run(int index);
    }
}
