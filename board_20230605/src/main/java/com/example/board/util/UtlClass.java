package com.example.board.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtlClass {
    // 2. UtlClass를 작성한다
    public static String dataFormat(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"));
        }
    }
}
