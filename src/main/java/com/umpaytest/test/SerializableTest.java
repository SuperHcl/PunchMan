package com.umpaytest.test;

import com.umpaytest.Car;

import java.io.*;

/**
 * @author: Hucl
 * @date: 2019/4/16 18:05
 * @description:
 */
public class SerializableTest {
    public static void main(String[] args) throws Exception {
        serializeCar();
        Car car = deserializeCar();
        System.out.println(car.toString());
    }

    /**
     * 在序列化的过程中，序列化对象的静态属性和 transient修饰的属性不可被序列化
     * 对象序列化是一个用于将对象状态转换为字节流的过程，可以将其保存到磁盘文件中或通过网络发送到任何其他程序；
     * 从字节流创建对象的相反的过程称为反序列化。
     * 而创建的字节流是与平台无关的，在一个平台上序列化的对象可以在不同的平台上反序列化。
     * @throws IOException
     */
    //序列化
    private static void serializeCar() throws IOException {
        Car car = new Car();
        car.setCar("0000");
        car.setName("法拉利");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("C:\\Users\\Hucl\\Desktop\\car.txt")));
        outputStream.writeObject(car);

        System.out.println("car对象序列化成功");
        outputStream.close();

    }

    //反序列化
    private static Car deserializeCar() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("C:\\Users\\Hucl\\Desktop\\car.txt")));
        Car car = (Car) inputStream.readObject();
        System.out.println("car对象反序列化成功");
        return car;
    }
}
