package sx.reece.javakit.modern;

import sx.reece.javakit.utils.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * | Author: Reece Wilson (Reece.SX @not_rsx)
 * | Type: Work in progress
 * | License:
 * |  Copyright (C) Reece.SX - MIT
 */
public class ModernString {
    private String string;

    public ModernString(String string) {
        this.string = string;
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    public static ModernString insertPeriodically(
            String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                text.length() + insert.length() * (text.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index,
                    Math.min(index + period, text.length())));
            index += period;
        }
        return new ModernString(builder.toString());
    }

    public static ModernString join(String joiner, byte[] data) {
        List<Byte> list = new ArrayList<>();
        for (byte b : data) list.add(b);
        return join(joiner, list);
    }

    public static ModernString join(String joiner, List<Byte> data) {
        String a = data.stream().map((item) -> DataUtils.getHex(new byte[]{item})).collect(Collectors.joining("-"));
        return join(joiner, null, a);
    }

    public static ModernString join(String joiner, Object... data) {
        return join(joiner, null, data);
    }

    private static ModernString join(String joiner, String replaceLast, Object[] data) {
        String str = Arrays.stream(data).map(item -> item == null ? "null" : item.toString()).collect(Collectors.joining(joiner));
        if (str.startsWith("null" + joiner)) str = str.replaceFirst("null" + joiner, "");
        if (str.contains(joiner + "null" + joiner)) str = str.replaceAll(joiner + "null" + joiner, "");
        return new ModernString(replaceLast != null ? replaceLast(str, replaceLast, "") : str);
    }

    public static ModernString fromString(String str) {
        return new ModernString(str);
    }

    public ModernString replaceLast(String regex, String replacement) {
        string = replaceLast(string, regex, replacement);
        return this;
    }

    public ModernString insertPeriodically(String insert, int period) {
        string = insertPeriodically(string, insert, period).getString();
        return this;
    }

    @Override
    public String toString() {
        return string;
    }

    public String getString() {
        return string;
    }
}
