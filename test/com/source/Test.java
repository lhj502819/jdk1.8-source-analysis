package com.source;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/7/28
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDate.now().minusDays(1));;
        String sign = null;
        try {
            String beforeYestimeStampEnd = String.valueOf(LocalDateTime.of(LocalDate.now().minusDays(2),LocalTime.MAX).atZone(ZoneOffset.of("+8")).toEpochSecond());

            Map<String, Object> params = new LinkedHashMap<>();
            Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
            calendar.add(Calendar.DAY_OF_MONTH, -0); //当前时间减去一天，即一天前的时间
            long timeInMillis = calendar.getTimeInMillis();//返回当前时间的毫秒数
            Date date = new Date(timeInMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = sdf.format(getStartOfDay(date));
            startTime = dateToString(startTime);
            System.out.println(startTime);
            String endTime = sdf.format(getEndOfDay(date));
            endTime = dateToString(endTime);
            System.out.println(endTime);
            sdf.format(getEndOfDay(date));
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String nowDate = dateToString(sdf.format(new Date()));
            System.out.println(nowDate);
            params.put("appid", "922");
            params.put("msgId", "msgId");
            params.put("ts", nowDate);
            params.put("videoIds", "M2020082415411980400020113000000");
//            params.put("st", startTime);
            String reqParam = paramToString(params);
            System.out.println(reqParam);
            sign = hmacsha256(reqParam, "ssFL438Uyk9HWUV27/mjJFDhQKrelyXLXhoffrUvDik=");
            System.out.println(sign);
        } catch (Exception e) {
//                    iXIxv+r7EMtSBm7jYE8n0PqT0jdmAMjJC8GhoKEnqoI=
//                    ssFL438Uyk9HWUV27/mjJFDhQKrelyXLXhoffrUvDik=
            e.printStackTrace();
        }
    }




    public static String dateToString(String s) {
        String replace = s.replace("-", "");
        replace = replace.replace(":", "");
        replace = replace.replace(" ", "");
        return replace;
    }


    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String hmacsha256(String data, String secret)
            throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HMACSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException("签名失败", gse);
        }
        return Base64.getEncoder().encodeToString(bytes);
    }


    private static String paramToString(Map<String, Object> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)) {
                sb.append(k + v);
            }
        }
        return sb.toString().trim();
    }

}
