package com.umpaytest.service.test;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.joekerouac.common.tools.constant.StringConst;
import com.google.common.collect.Lists;

import com.google.common.collect.Sets;
import com.umpaytest.entity.User;
import com.umpaytest.entity.VideoEntity;
import com.umpaytest.util.TimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hu.ChangLiang
 * @date 2022/5/26 10:22
 */
public class CollectionTest {

    @Test
    public void set2list() {
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        ArrayList<Long> longs = new ArrayList<>(ids);
        System.out.println(longs);
    }

    @Test
    public void fileTest() throws IOException {
        String path = "/Users/longlighthu/Desktop/test.txt";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        for (int i = 0; i < file.length(); i++) {
            char ch = (char) (fileInputStream.read());//循环读取字符
            System.out.print(ch + " ");
        }
        System.out.println();//换行操作
    }

    @Test
    public void map_test() {
        Map<String, Integer> map = new HashMap<>();
        List<String> list = Arrays.asList("1", "2", "1");
        for (String s : list) {
            Integer integer = map.putIfAbsent(s, 1);
            if (integer != null) {
                map.put(s, ++integer);
            }
        }

        System.out.println(map);

        Integer a = 1;
        Integer b = null;
        System.out.println(Arrays.asList(a, b));
        System.out.println(CollectionUtils.isNotEmpty(Arrays.asList(b)));
    }

    @Test
    public void double_test() {
//        double seed = ThreadLocalRandom.current().nextDouble(1, 1000);
//        double res = 1000d + seed;
//
//        System.out.println(res);
        double d1 = 10039.097634455304d;
        double d2 = 10096.607037877548d;
        double d3 = (d1 + d2) / 2;
        double d4 = (d1 + d2) / 2.0;
        System.out.println(d3);
        System.out.println(d4);

        System.out.println(Objects.equals(null, null));

        throw new UnsupportedOperationException(StrUtil.format("字典值移动失败，移动的数据不正确。targetLevel={}, " +
                        "targetParentId={}, operateLevel={}, operateParentId={}", 1, 11,
                1, 11));
    }

    @Test
    public void test_group() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i);
            user.setAge(i * 6);
            if (i % 3 == 0) {
                user.setName("张三3");
            } else if (i % 2 == 0) {
                user.setName("张三2");
            } else if (i % 5 == 0) {
                user.setName("张三5");
            } else {
                user.setName(i + "");
            }


            users.add(user);
        }

        Map<String, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println(JSON.toJSONString(collect));

    }

    @Test
    public void test_like_str() {
        String str = "展示岗位";

        System.out.println(str.contains("岗"));
        System.out.println(str.contains("展示"));

        String str1 = "12345";
        System.out.println(str1.contains("2"));
        System.out.println(str1.contains("6"));
    }

    @Test
    public void split_collection() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
//        list.add("f");

        List<String> strings = list.subList(5, list.size());
        System.out.println(list.size());
        System.out.println(list.get(list.size() - 1));
        System.out.println(strings);
        System.out.println("-------------------------");
        List<String> strings1 = subForList(list, 1, 2);
        System.out.println(strings1);

        List<String> strings2 = subForList(list, 2, 2);
        System.out.println(strings2);

        List<String> strings3 = subForList(list, 5, 2);
        System.out.println(strings3);
    }


    private List<String> subForList(List<String> treeRecord, long current, long size) {
        List<String> result = Lists.newArrayList();
        int fromIndex = 0, toIndex = 0;
        int dataSize = treeRecord.size();

        if (current == 1) {
            toIndex = (int) (Math.min(size, dataSize));
        } else if (current > 1) {
            fromIndex = (int) ((current - 1) * size);
            fromIndex = Math.min(fromIndex, dataSize);
            toIndex = (int) (Math.min(size * current, dataSize));
        }

        try {
            result = treeRecord.subList(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(StrUtil.format("list截取失败，listSize={}, fromIndex={}，toIndex={}, current={}, size={}", treeRecord.size(), fromIndex,
                    toIndex, current, size));
        }
        return result;
    }

    @Test
    public void test_11() {
        String a = "CmFwaVZlcnNpb246IHYxCmNsdXN0ZXJzOgotIGNsdXN0ZXI6CiAgICBpbnNlY3VyZS1za2lwLXRscy12ZXJpZnk6IHRydWUKICAgIHNlcnZlcjogaHR0cHM6Ly8xOTIuMTY4LjE4NS4yOjY0NDMKICBuYW1lOiBob3N0CmNvbnRleHRzOgotIGNvbnRleHQ6CiAgICBjbHVzdGVyOiBob3N0CiAgICB1c2VyOiBrYy1zZXJ2ZXIKICBuYW1lOiBrYy1zZXJ2ZXItaG9zdApjdXJyZW50LWNvbnRleHQ6IGtjLXNlcnZlci1ob3N0CmtpbmQ6IENvbmZpZwpwcmVmZXJlbmNlczoge30KdXNlcnM6Ci0gbmFtZToga2Mtc2VydmVyCiAgdXNlcjoKICAgIHRva2VuOiBleUpoYkdjaU9pSlNVekkxTmlJc0ltdHBaQ0k2SW1semVqRjJTbEpuYzNWWGRFOTZXVFY1UWtFME1VNXZWa2MwUVVod1JGOXpRelJwYWxaU1dWbHBaVkVpZlEuZXlKcGMzTWlPaUpyZFdKbGNtNWxkR1Z6TDNObGNuWnBZMlZoWTJOdmRXNTBJaXdpYTNWaVpYSnVaWFJsY3k1cGJ5OXpaWEoyYVdObFlXTmpiM1Z1ZEM5dVlXMWxjM0JoWTJVaU9pSnJkV0psTFhONWMzUmxiU0lzSW10MVltVnlibVYwWlhNdWFXOHZjMlZ5ZG1salpXRmpZMjkxYm5RdmMyVmpjbVYwTG01aGJXVWlPaUpyWXkxelpYSjJaWEl0ZEc5clpXNHRlSGQ0ZUhJaUxDSnJkV0psY201bGRHVnpMbWx2TDNObGNuWnBZMlZoWTJOdmRXNTBMM05sY25acFkyVXRZV05qYjNWdWRDNXVZVzFsSWpvaWEyTXRjMlZ5ZG1WeUlpd2lhM1ZpWlhKdVpYUmxjeTVwYnk5elpYSjJhV05sWVdOamIzVnVkQzl6WlhKMmFXTmxMV0ZqWTI5MWJuUXVkV2xrSWpvaU5qZzFaR0kzTmprdE1UUmlOaTAwTWprNExUazJZVGN0TmpkaU5HVXlaRGRrTkdVMUlpd2ljM1ZpSWpvaWMzbHpkR1Z0T25ObGNuWnBZMlZoWTJOdmRXNTBPbXQxWW1VdGMzbHpkR1Z0T210akxYTmxjblpsY2lKOS5ETURaOGdzUXF4dzduaDc5QWdMeFNQZGJ2eGFTWEhWbldjWDZHV0Q0a3NHZUlLdzlYNUE5eTM4SENCSkdELXgtUFM5THMyZi02eWFmOFVzb3JSVWNDMTFoUTgyTnpjZGU0ZzJqa1VXZUUwZE9oTmxpTmxxcDV4WHZiQmZLNElFZUZuWmFWWEk2VklQVjJ3WE1YZG5YRlN6dVdOUGtvX1QzTHZPZkZGN19pZWxSNzg5RkFvWEpGams0bE12NzVoOFFJMF9ITm8xYWMyWEZGU0s5WGdIQzRULWJ6M0pHS1dsTi1uNHlseFlJUUtDeVNWbUU5OFcxUnFLc1FPY1lnbFI0TWZYc3BMcUJtQl9MQ2NXZlc3TEduOVowSkkyQzE4cXE5bkpfblAtOTBVZFZvY29kdDJtM2IycDkwRS1zUmlJNGplcFVaQk1OQnBjbW1ZQ2ExakJXc2c=";
        byte[] decode = Base64.getDecoder().decode(a);
        System.out.println(new String(decode));
    }

    @Test
    public void test_12() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = new Date();
        Date a = new Date(System.currentTimeMillis() - 1000000L);

        System.out.println(now.after(a));

        System.out.println("now: " + format.format(now));
        System.out.println("before: " + format.format(a));

    }

    @Test
    public void test_13() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Integer a = map.putIfAbsent("a", 4);
        System.out.println(a);

        map.put(null, 5);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test_14() {
        int a = 1;
        String b = "b";

        Exception exception = null;

        String format = String.format("请求第三方底层失败，httpStatus=[%d], response=[%s], exception=[%s]", a, b, exception);
        System.out.println(format);
    }

    @Test
    public void test_15() {
        List<String> strings = null;
        if (CollectionUtils.isEmpty(strings) || strings.contains("s")) {
            System.out.println("aasdafsf");
        }
        System.out.println(Objects.equals(null, "1"));
        System.out.println(DateUtil.offset(DateUtil.date(), DateField.DAY_OF_MONTH, -1));

    }

    @Test
    public void test_16() {
        User u1 = new User(3, "小红");
        User u2 = new User(3, "小红");
        User u3 = new User(6, "小黑");
        User u4 = new User(16, "小子");

        List<User> users = Arrays.asList(u1, u2, u3, u4);
        System.out.println(users);

        String collect = users.stream().map(user -> {
                    StringBuilder message = new StringBuilder();
                    if (3 == user.getId()) {
                        message.append("user id ").append(3).append(" =3;");
                    }
                    if ("小黑".equals(user.getName())) {
                        message.append("user name ").append(user.getName()).append(" 是小黑;");
                    }
                    return message.toString();
                })
                .collect(Collectors.joining());
        System.out.println(collect);
    }

    @Test
    public void test_18() {
        long intervalMilliSeconds = 16 * 60 * 1000;
        long start = System.currentTimeMillis() - intervalMilliSeconds;

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start).toString());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(1675759786164L).toString());

        System.out.println(start);
    }

    @Test
    public void test_19() {
        User user = new User();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        for (Integer integer : integers) {
            user.setAge(user.getAge() + integer);
        }
        System.out.println(user);
        float f = 34.23453f;
        DecimalFormat decimalFormat = new DecimalFormat("##0.0000");
        String format = decimalFormat.format(f);
        System.out.println(format);
    }

    @Test
    public void test_20() {
        String earliestTime = "20230201";
        if (StringUtils.isNotEmpty(earliestTime)) {
            LocalDate date = LocalDate.parse(earliestTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
            earliestTime = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        System.out.println(earliestTime);
    }

    @Test
    public void test_21() {
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);

        Long[] longs1 = longs.toArray(new Long[0]);
        System.out.println(Arrays.toString(longs1));

        String[] split = "ZZYFZX_2023021618.1.".split("\\.");
        String orgCode = split[0] + "." + split[1] + ".";
        System.out.println(orgCode);

        String format = LocalDateTime.parse("2023-03-09 17:00:00", DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(1L)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
    }

    @Test
    public void test_22() {
        // 记录时间
        String recordHour = "";
        // 计量周期，格式:yyyyMM
        String meteringCycle = "";
        // 计量日期，格式:yyyyMMdd
        String meteringDate = "";
        //计量结束时间
        String meteringEndTime = "";
        String meteringStartTime = "";

        Date now = new Date();
        Date lastHourDate = TimeUtil.addDateHours(now, -1);
        String lastHour = TimeUtil.format(lastHourDate, TimeUtil.DATE_TIME_HOUR_PATTERN);
        String curHour = TimeUtil.format(now, TimeUtil.DATE_TIME_HOUR_PATTERN);
        recordHour = TimeUtil.format(lastHourDate, TimeUtil.HOUR_PATTERN);


        meteringCycle = TimeUtil.format(lastHourDate, TimeUtil.DATE_YEARMONTH_PATTERN);
        meteringDate = TimeUtil.format(lastHourDate, TimeUtil.DATE_YEARMONTHDAY_PATTERN);
        //计量开始时间
        meteringStartTime = lastHour + ":00:00";
        meteringEndTime = curHour + ":00:00";

        System.out.println(meteringStartTime);
    }

    @Test
    public void test_24() {
        User u1 = new User(3, "小红");
        User u2 = new User(3, "小红");
        User u3 = new User(6, "小黑");
        User u4 = new User(6, "小子");

        List<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);

        Map<Integer, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getId));


        List<User> orDefault = collect.getOrDefault(4, new ArrayList<>());
        System.out.println(orDefault.stream().map(User::getName).collect(Collectors.toList()));

    }

    @Test
    public void test_25() {
        BigDecimal p = new BigDecimal("5");
        BigDecimal flavorPrice = p.divide(new BigDecimal("24"), 8, RoundingMode.HALF_UP);
        BigDecimal flavorCost = flavorPrice.multiply(new BigDecimal("1.0"));
//        BigDecimal flavorDiscountCost = flavorPrice.subtract(flavorCost).setScale(8, RoundingMode.HALF_UP);
        BigDecimal flavorDiscountCost = flavorPrice.subtract(flavorCost);

        System.out.println(flavorDiscountCost);
    }

    public static String aa() {
        do {
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    return "ssa";
                }
            }
        } while (true);
    }

    @Test
    public void test_26() {
//        String s = "am9obg==";
        String s = "5bCP6I235bCW5bCW8J+Yjg==";
        byte[] decode = Base64.getDecoder().decode(s);
//        byte[] decode = Base64Utils.decode(s.getBytes(StandardCharsets.UTF_8));
        String s1 = new String(decode);
        System.out.println(s1);
    }

    @Test
    public void test_27() {
        BigDecimal cent = new BigDecimal(6010456);
        String a = cent.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).toString();
        System.out.println(a);
    }

    @Test
    public void test_28() {
//        long t  = 1711587092841L;
        long t = 1711592635181L;
        // 转换为Instant对象
        Instant instant = Instant.ofEpochMilli(t);

        // 设置时区为中国标准时间（东八区，即北京时间）
        ZoneId zoneId = ZoneId.systemDefault();

        // 将Instant转换为特定时区的ZonedDateTime对象
        ZonedDateTime dateTime = instant.atZone(zoneId);

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 输出格式化后的日期时间字符串
        String formattedDateTime = dateTime.format(formatter);
        System.out.println(formattedDateTime);
    }

    @Test
    public void test_29() {
        Date date = new Date();
        String format = String.format("%s:%s:%d:%d", date, null, 1L, 2L);
        String encode = Base32.encode(format.getBytes(StandardCharsets.UTF_8));
        byte[] decode = Base32.decode("IZZGSICNMFZCAMBREAYDOORUHA5DCNBAINJVIIBSGAZDIOSNN5XCATLBOIQDENJAGA3TUNBYHIYTIICDKNKCAMRQGI2DU3TVNRWDU3TVNRWA");
        System.out.println(format);
        System.out.println(encode);
        System.out.println(new String(decode));
    }

    @Test
    public void test_30() {
        List<String> reservationDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int nonSundayCount = 0;

        // 设置初始日期为订单支付时间次日
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 寻找并添加3个连续的非周日日期
        while (nonSundayCount < 3) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                String format = DateUtil.format(calendar.getTime(), DatePattern.NORM_DATE_PATTERN);
                reservationDates.add(format);
                nonSundayCount++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        System.out.println(reservationDates);
    }

    @Test
    public void test_31() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date payTime = simpleDateFormat.parse("2024-05-14 00:00:00");
        Date now = simpleDateFormat.parse("2024-05-15 15:19:00");
        List<String> reservationDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int nonSundayCount = 0;

        // 设置初始日期为订单支付时间次日
        calendar.setTime(payTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 寻找并添加3个连续的非周日日期
        while (nonSundayCount < 3) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                Date time = calendar.getTime();
                if (DateUtil.isSameDay(time, now) || time.after(now)) {
                    String format = DateUtil.format(calendar.getTime(), DatePattern.NORM_DATE_PATTERN);
                    reservationDates.add(format);
                }
                nonSundayCount++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println(reservationDates);
    }

    @Test
    public void test_32() {
        List<String> l = new ArrayList<>();
        l.add("1L");
        l.add("2L");
        l.add("3L");
        List<String> collect = l.stream().filter(s -> s.equals("1L")).collect(Collectors.toList());
        System.out.println(collect);

        for (String aLong : l) {
            HashSet<String> tempSet = Sets.newHashSet(StringUtils.split(aLong, ","));
            System.out.println(tempSet);
        }
    }

    @Test
    public void test_33() {
        List<User> userList = new ArrayList<>();
        User u = new User();
        u.setId(1);
        u.setAge(10);
        u.setName("ajs");

        User u1 = new User();
        u1.setAge(12);
        u1.setName("wew1");

        User u2 = new User();
        u2.setId(null);
        u2.setAge(8);
        u2.setName("wew");

        User u3 = new User();
        u3.setId(2);
        u3.setAge(6);
        u3.setName("wew");

        userList.add(u);
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

        Map<String, Map<Integer, User>> collect = userList.stream().collect(Collectors.groupingBy(User::getName, Collectors.toMap(
                        User::getId,
                        e -> e, (e1, e2) -> e1
                ))
        );

        System.out.println(collect);
    }

    private static void print(Integer i) {
        System.out.println(Objects.isNull(i));
        System.out.println(i);
    }

    @Test
    public void test_34() {
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0);
        System.out.println(now);
        // 获取当天00:00的LocalDateTime
        LocalDateTime startOfInterval = LocalDateTime.of(now.toLocalDate(), LocalTime.of(0, 0));
        // 获取当天00:10的LocalDateTime
        LocalDateTime endOfInterval = LocalDateTime.of(now.toLocalDate(), LocalTime.of(0, 10));

        System.out.println(startOfInterval);
        System.out.println(endOfInterval);
        System.out.println("------------------");

        System.out.println(now.isAfter(startOfInterval));

        String format = String.format("You can only make an appointment to pick up the goods %d hours later.", 3);
        System.out.println(format);
    }

    @Test
    public void test_35() {
        String pickupDate = "2024-05-15";
        String pickupHour = "22:10-23:00";
        LocalDateTime localDateTime = LocalDateTime.now();
        boolean b;
        try {
            String puStartTime = pickupHour.split("-")[0];
            LocalDateTime pickupDateTime = DateUtil.parseLocalDateTime(String.format("%s %s", pickupDate, puStartTime), DatePattern.NORM_DATETIME_MINUTE_PATTERN);
            b = pickupDateTime.isAfter(localDateTime.plusHours(3));
        } catch (Exception e) {

            b = false;
        }
        System.out.println(b);
    }

    @Test
    public void test_36() {
        List<String> orderCodes = new ArrayList<>();
        orderCodes.add("OGVN2024061400023");
        Map<String, Object> mapc = new HashMap<>();
        mapc.put("orderCodes", orderCodes);
        mapc.put("orgId", 1);
        mapc.put("statusFlag", "1");
        System.out.println(JSON.toJSONString(mapc));

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("uid", -1);
        map1.put("zoneId", "Asia/Ho_Chi_Minh");
        map1.put("orgId", 1);

        map.put("traceContext", JSON.toJSONString(map1));

        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test_37() {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();

        map1.putAll(map2);
        System.out.println(map1.size());
        System.out.println(map1);
    }


    @Test
    public void test_38() {
        JSONObject json = new JSONObject();
        json.put("key1", "C:\\Users\\20573\\Desktop\\电子发票.pdf");
        System.out.println(json.toJSONString());
    }

    @Test
    public void test_39() {
        long utcTimestamp = System.currentTimeMillis();
        Date date = new Date(utcTimestamp);
        System.out.println("UTC timestamp: " + utcTimestamp);
        System.out.println("Local time:     " + date.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC formatted:  " + formatter.format(date));
    }

    @Test
    public void test_40() {
        String regex = "^([01]?[0-9]|2[0-3]):00-([01]?[0-9]|2[0-3]):00$";
        Pattern pattern = Pattern.compile(regex);

        // 测试数据
        String[] testStrings = {"10:00-11:00", "00:00-23:00", "12:00-12:00", "23:00-00:00", "10:15-11:00", "10:00-11"};

        for (String s : testStrings) {
            Matcher matcher = pattern.matcher(s);
            boolean isValid = matcher.matches();
            System.out.println("Testing: " + s + ", Valid: " + isValid);
        }
    }

    @Test
    public void test_41() {
        String appointmentDay = "2024-01-01";
        String appointmentHour = "09:00-10:00";

        // 格式化 appointmentDay
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDay, formatterDay);

        // 格式化 appointmentHour
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(appointmentHour.split("-")[0], formatterHour);
        LocalTime endTime = LocalTime.parse(appointmentHour.split("-")[1], formatterHour);

        // 将日期和时间转换为所需的格式
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("hhmm"));
        String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("hhmm"));

        String s = formattedDate + formattedStartTime + "_" + formattedEndTime;
        System.out.println(s);
    }

    @Test
    public void test_42() {
        Clock clock = Clock.systemDefaultZone();
        LocalTime SOA_CHECK_TIME = LocalTime.of(1, 0, 0);
        LocalDate now = LocalDate.now(clock);
        // 当天的对账任务在第二天执行
        LocalDate runDate = now.plus(1, ChronoUnit.DAYS);
        LocalDateTime execTime = runDate.atTime(SOA_CHECK_TIME);
        System.out.println(execTime);
    }

    @Test
    public void test_43() {
        String SPLIT = new String(new byte[]{0x03}, StandardCharsets.UTF_8);
        String nextLine = "\u0003\u0003\u0003\u0003\u0003\u00031835845530228383746\u0003\u0003\u00031835845530228383746\u00030\u0003\u0003484200\u00030\u0003\u0003false\u0003\u00030\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003";
        String[] split = nextLine.split(SPLIT, -1);

        System.out.println(split);
    }

    @Test
    public void test_44() {
        String originalFilename = "百度一下.pdf";
        String suffix = originalFilename.substring(0, originalFilename.lastIndexOf(StringConst.DOT));
        System.out.println(suffix);
        String s = "IdWorker.getIdStr()" + StringConst.DOT + suffix;
        System.out.println(s);
        System.out.println(s.equalsIgnoreCase(null));
    }

    @Test
    public void test_45() {
        List<String> strings = Arrays.asList("1", "2", "3", "1");
        Set<String> set = new HashSet<>();
        for (String s : strings) {
            boolean add = set.add(s);
            System.out.println(s + "--" + add);
        }
    }

    @Test
    public void test_46() {
        double showTime = 3.001;
        // 上报时长的处理系数 101=百分之1
        int showTimeFactor = 101;

        // 将 showTimeFactor 转换为小数（例如 101 转换为 1.01）
        double factor = showTimeFactor / 100.0;

        // 计算处理后的时长
        double v = showTime * factor;
        BigDecimal bigDecimal = new BigDecimal(v).setScale(3, RoundingMode.HALF_UP);

        System.out.println(showTime * factor);
        System.out.println(bigDecimal.doubleValue());
    }

    @Test
    public void test_47() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取当前时间的前一年时间
        LocalDateTime oneYearAgo = now.minusYears(1);
        String createDateStart = oneYearAgo.format(formatter);
        String createDateEnd = now.format(formatter);

        System.out.println(createDateStart);
        System.out.println(createDateEnd);
    }

}
