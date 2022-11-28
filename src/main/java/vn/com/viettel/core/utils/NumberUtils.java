package vn.com.viettel.core.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    private NumberUtils(){

    }

    public static String format(long number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String res = formatter.format(number);
        System.out.print(res);
        return res;
    }

    public static String format(long number, String formatter) {
        String formatStr = formatter == null ? "###,###,###" : formatter;
        DecimalFormat format = new DecimalFormat(formatStr);
        return format.format(number);
    }
}
