package br.com.pay.wallet.util;

import java.time.LocalDate;

public class DateUtil {

    public static int toNumeric(LocalDate toConvert) throws Exception {
        return Integer.parseInt(""+toConvert.getYear()+toConvert.getMonthValue()+toConvert.getDayOfMonth());
    }
}
