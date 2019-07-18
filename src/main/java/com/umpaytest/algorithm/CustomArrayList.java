package com.umpaytest.algorithm;

/**
 * @author: Hucl
 * @date: 2019/7/18 09:36
 * @description:
 */
public class CustomArrayList {

    private Object[] data;
    private int size = 0;

    public CustomArrayList() {
        // 默认数组大小为10
        this(10);
    }

    public CustomArrayList(int length) {
        data = new Object[length];
    }

    public void add(Object obj) {
        ensureCapacityInternal(size + 1);
        data[size++] = obj;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (minCapacity - data.length > 0) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        // 扩容，增加为之前的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 处理new List(0)的时候，也就是当初始化数组长度为0，然后又进行add操作的时候。
        if (minCapacity - newCapacity > 0) {
            newCapacity = minCapacity;
        }
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    public Object get(int index) {
        rangeCheck(index);

        return data[index];
    }

    public void remove(int index) {
        rangeCheck(index);

        fastRemove(index);
//        int length = data.length;
//        System.arraycopy(data, index + 1, data, index, length - index - 1);
//        data[--size] = null;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException();
    }

    public boolean remove(Object val) {
        if (val == null) {
            for (int index = 0; index < size; index++) {
                if (data[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (data[index].equals(val)) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;

    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        // let GC do its work
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < size; index++) {
            sb.append(data[index]).append("--");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomArrayList list = new CustomArrayList();
        list.add(10);
        list.add(2);
        list.add(160);
        list.add(22);
        list.add(99);

        System.out.println(list.toString());
        System.out.println(list.get(4));

        list.remove(4);
        System.out.println(list.toString());
        list.clear();
        System.out.println();
    }

}
