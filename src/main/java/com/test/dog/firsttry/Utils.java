//Функции - утилиты

package com.test.dog.firsttry;

import java.time.LocalDateTime;
import java.util.Date;

public class Utils {

    public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }

}
