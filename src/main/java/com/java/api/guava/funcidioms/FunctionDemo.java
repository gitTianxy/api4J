package com.java.api.guava.funcidioms;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import java.util.Map;

/**
 * 含义
 * Function<A, B>，它声明了单个方法B apply(A input): convert A(input) to B(output)
 *
 * 应用
 * 函数最常见的用途为转换集合。
 *
 * Functions(util)
 * 1. forXXX: return instance of Function based on input
 * 2. compose: build up a compose-function based on input functions
 * 3. constant(E const): 返回一个常函数 f(x)=const
 * 4a. identity(): 返回输入值本身 f(x)=x
 * 4b. toStringFunction(): 返回这样一个函数 f(x)=x.toString()
 *
 * Created by kevintian on 2017/10/16.
 */
public class FunctionDemo {

    /**
     * 作用:
     * 以一个映射 map 为入参，返回这样一个函数，f(x)=map.get(x)
     *
     * 形式:
     * 1. forMap(java.util.Map<K,V> map): throw exception when getting absent key
     * 2. forMap(java.util.Map<K,V> map, V default): return default when getting absent key
     */
    public <K, V> Function<K, V> forMap(Map<K, V> map) {
        return Functions.forMap(map, null);
    }

    /**
     * 构造复合函数
     * compose(Function<B, C> g(x), Function<A, B> f(x))
     *
     */
    public <A, B, C> Function<A, C> compose(Function<A, ? extends B> f, Function<B, C> g) {
        return Functions.compose(g, f);
    }

    /**
     * 常量函数
     *
     * @param constant
     * @param <E>
     * @return
     */
    public <E> Function<Object, E> constant(E constant) {
        return Functions.constant(constant);
    }
}
