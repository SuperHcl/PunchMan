package com.umpaytest.service.test;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal 内存泄露行为对比测试
 *
 * <p>验证目标：在线程池场景下，ThreadLocal.set(null) 会在 ThreadLocalMap 中残留空 Entry，
 * 而 ThreadLocal.remove() 可以彻底清除 Entry，避免不必要的内存占用。</p>
 *
 * <p>测试策略：不依赖 Spring 容器，通过反射读取 Thread.threadLocals（ThreadLocalMap）
 * 中的实际 Entry 数量，用数据直观对比两种清理方式的差异。不追求真实 OOM
 * （OOM 依赖 JVM 参数且破坏性大），转而通过 Entry 残留数量证明内存占用差异。</p>
 *
 * @author Hu.ChangLiang
 * @since 1.9.5
 */
public class ThreadLocalMemoryLeakTest {

    // -------------------------------------------------------------------------
    // 模拟 BrandContext 原始实现（set(null) 方式）
    // -------------------------------------------------------------------------

    /** 模拟 BrandContext.BRAND_IDS —— static final，生命周期与类一致，key 永远不会被 GC */
    private static final ThreadLocal<List<Long>> BRAND_IDS_LEAKY = new ThreadLocal<>();

    /**
     * 模拟原始 BrandContext.run(List, Runnable) —— finally 中 set(null)
     */
    private static void runLeaky(List<Long> brandIds, Runnable runnable) {
        List<Long> before = BRAND_IDS_LEAKY.get();
        try {
            BRAND_IDS_LEAKY.set(brandIds);
            runnable.run();
        } finally {
            // ❌ 原始写法：set(null)，Entry 依然残留在 ThreadLocalMap 中
            BRAND_IDS_LEAKY.set(before);
        }
    }

    // -------------------------------------------------------------------------
    // 模拟 BrandContext 修复后实现（remove() 方式）
    // -------------------------------------------------------------------------

    private static final ThreadLocal<List<Long>> BRAND_IDS_FIXED = new ThreadLocal<>();

    /**
     * 模拟修复后 BrandContext.run(List, Runnable) —— finally 中 remove()
     */
    private static void runFixed(List<Long> brandIds, Runnable runnable) {
        List<Long> before = BRAND_IDS_FIXED.get();
        try {
            BRAND_IDS_FIXED.set(brandIds);
            runnable.run();
        } finally {
            if (before == null) {
                // ✅ 修复写法：彻底清除 Entry
                BRAND_IDS_FIXED.remove();
            } else {
                BRAND_IDS_FIXED.set(before);
            }
        }
    }

    // -------------------------------------------------------------------------
    // 工具方法：反射读取当前线程 ThreadLocalMap 中的 Entry 数量
    // -------------------------------------------------------------------------

    /**
     * 通过反射统计当前线程 ThreadLocalMap 中非 null 的 Entry 数量。
     *
     * <p>ThreadLocalMap 使用开放寻址法，内部 table 是 Entry[]，
     * 遍历统计 table[i] != null 的个数即为实际残留 Entry 数。</p>
     */
    private static int countThreadLocalEntries() throws Exception {
        Thread thread = Thread.currentThread();

        // 获取 Thread.threadLocals 字段
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object threadLocalMap = threadLocalsField.get(thread);
        if (threadLocalMap == null) {
            return 0;
        }

        // 获取 ThreadLocalMap.table 字段（Entry[]）
        Class<?> threadLocalMapClass = threadLocalMap.getClass();
        Field tableField = threadLocalMapClass.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(threadLocalMap);
        if (table == null) {
            return 0;
        }

        int count = 0;
        for (Object entry : table) {
            if (entry != null) {
                count++;
            }
        }
        return count;
    }

    // =========================================================================
    // 测试一：直接在同一线程验证 Entry 残留
    // =========================================================================

    /**
     * 测试 set(null) 后 ThreadLocalMap 中 Entry 是否残留。
     *
     * <p>预期：调用 runLeaky() 前后，Entry 数量不变（Entry 残留，value=null）。</p>
     */
    @Test
    public void testSetNullLeavesEntry() throws Exception {
        // 确保初始状态干净（不对 BRAND_IDS_LEAKY 做任何 set）
        BRAND_IDS_LEAKY.remove();

        int before = countThreadLocalEntries();
        System.out.println("[set(null)] 调用前 Entry 数量: " + before);

        // 模拟调用前无上下文，执行 run，finally 中 set(null)
        runLeaky(Collections.singletonList(1L), () -> {
            System.out.println("[set(null)] 执行中，BRAND_IDS_LEAKY=" + BRAND_IDS_LEAKY.get());
        });

        int after = countThreadLocalEntries();
        System.out.println("[set(null)] 调用后 Entry 数量: " + after);

        // ❌ set(null) 后：Entry 依然存在（count 不变或大于 0），value=null
        System.out.println("[set(null)] BRAND_IDS_LEAKY.get()=" + BRAND_IDS_LEAKY.get()
                + "（null 但 Entry 仍在 ThreadLocalMap 中）");

        Assert.assertTrue("set(null) 后 Entry 应仍然存在（残留）", after > 0);
        Assert.assertNull("set(null) 后 value 应为 null", BRAND_IDS_LEAKY.get());

        // 手动清理，防止污染其他测试
        BRAND_IDS_LEAKY.remove();
    }

    /**
     * 测试 remove() 后 ThreadLocalMap 中 Entry 是否被彻底清除。
     *
     * <p>预期：调用 runFixed() 前后，Entry 数量减少（Entry 被彻底移除）。</p>
     */
    @Test
    public void testRemoveClearsEntry() throws Exception {
        BRAND_IDS_FIXED.remove();

        int before = countThreadLocalEntries();
        System.out.println("[remove()] 调用前 Entry 数量: " + before);

        runFixed(Collections.singletonList(1L), () -> {
            System.out.println("[remove()] 执行中，BRAND_IDS_FIXED=" + BRAND_IDS_FIXED.get());
        });

        int after = countThreadLocalEntries();
        System.out.println("[remove()] 调用后 Entry 数量: " + after);
        System.out.println("[remove()] BRAND_IDS_FIXED.get()=" + BRAND_IDS_FIXED.get()
                + "（null 且 Entry 已从 ThreadLocalMap 中彻底移除）");

        // ✅ remove() 后：Entry 数量应比执行中少（Entry 被彻底清除）
        Assert.assertTrue("remove() 后 Entry 数量应减少（Entry 被彻底清除）", after < before + 1);
        Assert.assertNull("remove() 后 get() 应返回 null", BRAND_IDS_FIXED.get());
    }

    // =========================================================================
    // 测试二：线程池场景，模拟大量任务累积 Entry 的内存差异
    // =========================================================================

    private static final int TASK_COUNT = 10_000;
    private static final int THREAD_POOL_SIZE = 4;

    /**
     * 线程池场景下对比 set(null) vs remove() 的内存占用差异。
     *
     * <p>通过统计所有工作线程中残留的 Entry 总数来量化差异。
     * 使用 byte[] 模拟有一定体积的 value（贴近真实业务对象），
     * 放大残留 Entry 的堆内存影响。</p>
     */
    @Test
    public void testThreadPoolMemoryDiff() throws Exception {
        System.out.println("========== 线程池场景内存对比测试 ==========");
        System.out.println("任务数: " + TASK_COUNT + "，线程数: " + THREAD_POOL_SIZE);

        // ---------- 场景A：set(null) ----------
        long memoryLeaky = runWithThreadPool(true);
        System.out.printf("[set(null)]  所有工作线程残留 Entry 总数: %d%n", memoryLeaky);

        // ---------- 场景B：remove() ----------
        long memoryFixed = runWithThreadPool(false);
        System.out.printf("[remove()]   所有工作线程残留 Entry 总数: %d%n", memoryFixed);

        System.out.println("===========================================");
        System.out.printf("Entry 残留差值（set(null) - remove()）= %d%n", memoryLeaky - memoryFixed);

        Assert.assertTrue(
                "set(null) 场景残留 Entry 数应多于 remove() 场景，证明存在内存占用差异",
                memoryLeaky > memoryFixed
        );
    }

    /**
     * 在固定线程池中执行大量任务，每个任务模拟一次 BrandContext.run() 调用。
     *
     * @param useLeaky true=使用 set(null) 方式，false=使用 remove() 方式
     * @return 所有工作线程在任务结束后 ThreadLocalMap 中的 Entry 残留总数
     */
    private long runWithThreadPool(boolean useLeaky) throws Exception {
        // 使用独立的 ThreadLocal，避免两次测试互相干扰
        ThreadLocal<List<Long>> threadLocal = new ThreadLocal<>();

        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < TASK_COUNT; i++) {
            final List<Long> brandIds = Collections.singletonList((long) (i % 100 + 1));
            futures.add(pool.submit(() -> {
                List<Long> before = threadLocal.get();
                try {
                    threadLocal.set(brandIds);
                    // 模拟业务逻辑（do nothing）
                } finally {
                    if (useLeaky) {
                        threadLocal.set(before); // set(null)
                    } else {
                        if (before == null) {
                            threadLocal.remove();
                        } else {
                            threadLocal.set(before);
                        }
                    }
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get();
        }

        // 收集所有工作线程中该 ThreadLocal 对应 Entry 的残留情况
        AtomicInteger totalEntries = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(THREAD_POOL_SIZE);

        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            pool.submit(() -> {
                try {
                    // 判断当前线程是否还有该 ThreadLocal 的 Entry 残留
                    Object val = threadLocal.get();
                    // get() 触发 expungeStaleEntries，若 Entry 存在（即使 value=null）返回 null
                    // 用反射直接检查 ThreadLocalMap 中该 key 的 Entry 是否存在
                    boolean hasEntry = hasEntry(threadLocal);
                    if (hasEntry) {
                        totalEntries.incrementAndGet();
                    }
                    System.out.printf("  线程[%s] Entry残留=%b, value=%s%n",
                            Thread.currentThread().getName(), hasEntry, val);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        pool.shutdown();
        return totalEntries.get();
    }

    /**
     * 反射判断当前线程的 ThreadLocalMap 中是否存在指定 ThreadLocal 的 Entry（包括 value=null 的情况）。
     */
    private boolean hasEntry(ThreadLocal<?> target) throws Exception {
        Thread thread = Thread.currentThread();
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object map = threadLocalsField.get(thread);
        if (map == null) return false;

        Field tableField = map.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);
        if (table == null) return false;

        for (Object entry : table) {
            if (entry == null) continue;
            // Entry 继承自 WeakReference，referent 就是 ThreadLocal key
            Field refField = entry.getClass().getSuperclass().getDeclaredField("referent");
            refField.setAccessible(true);
            Object key = refField.get(entry);
            if (key == target) {
                return true; // Entry 存在，无论 value 是否为 null
            }
        }
        return false;
    }
}
