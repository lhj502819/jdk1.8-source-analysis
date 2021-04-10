package com.source.threadpool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/8/24
 */
public class test3 {
    CAS compare and swap
    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
        //开始时间
        String startTimeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startTime);
        //结束时间
        String endTimeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(endTime);
    }
}
