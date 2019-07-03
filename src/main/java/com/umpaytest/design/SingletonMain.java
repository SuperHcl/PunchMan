package com.umpaytest.design;

import com.umpaytest.design.singleton.*;

/**
 * @author: Hucl
 * @date: 2019/6/27 14:33
 * @description:
 */
public class SingletonMain {
    public static void main(String[] args) {
        HungryType hungryType = HungryType.getInstance();
        hungryType.say();

        EnumSingleton enumSingleton = EnumSingleton.getInstance();
        enumSingleton.say();

        InnerClassType innerClassType = InnerClassType.getInstance();
        innerClassType.say();

        SluggardType sluggardType = SluggardType.getInstance();
        sluggardType.say();

        SluggardType2 sluggardType2 = SluggardType2.getInstance();
        sluggardType2.say();
    }
}
