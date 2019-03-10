package sx.reece.javakit.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by RSX on 22/10/2016.
 */
public class ArrayUtils {
    public static byte[] toPrimitive(Byte[] buf) {
        byte[] ret = new byte[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static short[] toPrimitive(Short[] buf) {
        short[] ret = new short[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static float[] toPrimitive(Float[] buf) {
        float[] ret = new float[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static int[] toPrimitive(Integer[] buf) {
        return Arrays.stream(buf).mapToInt(item -> item).toArray();
    }

    public static long[] toPrimitive(Long[] buf) {
        return Arrays.stream(buf).mapToLong(item -> item).toArray();
    }

    public static double[] toPrimitive(Double[] buf) {
        return Arrays.stream(buf).mapToDouble(item -> item).toArray();
    }

    public static Double[] toObject(double[] buf) {
        Double[] ret = new Double[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Integer[] toObject(int[] buf) {
        Integer[] ret = new Integer[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Float[] toObject(float[] buf) {
        Float[] ret = new Float[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Long[] toObject(long[] buf) {
        Long[] ret = new Long[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Short[] toObject(short[] buf) {
        Short[] ret = new Short[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Byte[] toObject(byte[] buf) {
        Byte[] ret = new Byte[buf.length];
        for (int i = 0; i < buf.length; i++)
            ret[i] = buf[i];
        return ret;
    }

    public static Byte[] reverse(Byte[] data) {
        return (Byte[]) DataUtils.reverse(data);
    }

    public static Integer[] reverse(Integer[] data) {
        return (Integer[]) DataUtils.reverse(data);
    }

    public static Short[] reverse(Short[] data) {
        return (Short[]) DataUtils.reverse(data);
    }

    public static Double[] reverse(Double[] data) {
        return (Double[]) DataUtils.reverse(data);
    }

    public static Long[] reverse(Long[] data) {
        return (Long[]) DataUtils.reverse(data);
    }

    public static Float[] reverse(Float[] data) {
        return (Float[]) DataUtils.reverse(data);
    }

    public static List<Object> toList(Object[] list) {
        return Arrays.asList(list);
    }

    public static byte[] reverse(byte[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            byte temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }


    public static Object[] reverse(Object[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            Object temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }

    public static long[] reverse(long[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            long temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }

    public static double[] reverse(double[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            double temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }

    public static int[] reverse(int[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }

    public static short[] reverse(short[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            short temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }

    public static float[] reverse(float[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            float temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
        return data;
    }
}
