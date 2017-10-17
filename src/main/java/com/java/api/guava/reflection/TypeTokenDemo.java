package com.java.api.guava.reflection;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TypeToken是创建，操作，查询泛型类型（以及，隐含的类）对象的方法。
 *
 * Created by kevintian on 2017/10/17.
 */
public class TypeTokenDemo {
    public static void main(String[] args) throws NoSuchMethodException {
        // type erase on run
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();
        System.out.println("intList type is " + intList.getClass());
        System.out.println("stringList type is " + stringList.getClass());
        // type token
        System.out.println(StringUtils.center("ArrayList TypeToken", 50, "-"));
        TypeToken<ArrayList<String>> arrayListToken = new TypeToken<ArrayList<String>>() {};
        TypeToken<?> itemToken = arrayListToken.resolveType(ArrayList.class.getTypeParameters()[0]);
        System.out.println("item type: " + itemToken.getType());

        System.out.println(StringUtils.center("HashMap TypeToken", 50, "-"));
        TypeToken<HashMap<String, Object>> mapTypeToken = new TypeToken<HashMap<String, Object>>() {};
        TypeToken<?> keyToken = mapTypeToken.resolveType(HashMap.class.getTypeParameters()[0]);
        TypeToken<?> valToken = mapTypeToken.resolveType(HashMap.class.getTypeParameters()[1]);
        System.out.println(String.format("key type: %s, value type: %s", keyToken.getType(), valToken.getType()));
        TypeToken entrySetToken = mapTypeToken.resolveType(HashMap.class.getMethod("entrySet").getGenericReturnType());
        System.out.println("return type of entrySet(): " + entrySetToken.getType());
    }

}
