package com.umpaytest.service.test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.umpaytest.entity.MeteringUsageItemEntity;
import com.umpaytest.entity.MonitorK8sPodEsModel;
import com.umpaytest.entity.TestBean;
import com.umpaytest.entity.User;
import com.umpaytest.util.TimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        Integer d = map.get("d");
        System.out.println(d);

        Integer c = map.remove("d");
        System.out.println(c);

        System.out.println(map);

        String str = String.format("122, %s, %d", "aa", 88);
        System.out.println(str);

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
    }

    @Test
    public void test_17() {
        TestBean testBean = new TestBean();
        System.out.println(testBean);

        MonitorK8sPodEsModel monitorK8sPodEsModel = new MonitorK8sPodEsModel();
        System.out.println(monitorK8sPodEsModel);
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
                if (i==2) {
                    return "ssa";
                }
            }
        }while (true);
    }

}
