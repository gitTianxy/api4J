package com.java.api.guava.funcidioms;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 含义
 * Predicate<T>，它声明了单个方法boolean apply(T input): return boolean result based on certain/any type of input
 *
 * 作用
 * 断言的最基本应用就是过滤集合。
 *
 * 应用--在若干集合类相关的util中
 * 1. Iterables/Iterators: filter, retrieve, test-condition, delete
 * 2. Collections2: filter
 * 3. Maps: filter map by key, value, or entry
 * 4. ...
 *
 * Created by kevintian on 2017/10/16.
 */
public class PredicateDemo {
    /**
     * Iterables: filter iterable
     *
     * @param itr
     * @param filter
     * @param <T>
     * @return
     */
    public <T> Iterable<T> filter(Iterable<T> itr, Predicate<T> filter) {
        return Iterables.filter(itr, filter);
    }

    /**
     * Iterators: filter iterator
     *
     * @param itr
     * @param filter
     * @param <T>
     * @return
     */
    public <T> Iterator<T> filter(Iterator<T> itr, Predicate<T> filter) {
        return Iterators.filter(itr, filter);
    }

    /**
     * Collections2: filter
     *
     * @param col
     * @param filter
     * @param <T>
     * @return
     */
    public <T> Collection<T> filter(Collection<T> col, Predicate<T> filter) {
        return Collections2.filter(col, filter);
    }

    /**
     * Maps: filter by key
     *
     * @param map
     */
    public <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> filter) {
        return Maps.filterKeys(map, filter);
    }

    /**
     * Maps: filter by value
     *
     * @param map
     * @param filter
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> filter) {
        return Maps.filterValues(map, filter);
    }

    /**
     * Maps: filter by entry
     *
     * @param map
     * @param filter
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> filterByEntry(Map<K, V> map, Predicate<? super Map.Entry<K, V>> filter) {
        return Maps.filterEntries(map, filter);
    }


    /**
     * Iterators: find an element matching the condition
     *
     * @param itr
     * @param condition
     * @param defaultVal
     * @param <E>
     * @return
     */
    public <E> E find(Iterator<E> itr, Predicate<E> condition, E defaultVal) {
        return Iterators.find(itr, condition, defaultVal);
    }

    /**
     * Iterables: find an element matching the condition
     *
     * @param itr
     * @param condition
     * @param defaultVal
     * @param <T>
     * @return
     */
    public <T> T find(Iterable<T> itr, Predicate<T> condition, T defaultVal) {
        return Iterables.find(itr, condition, defaultVal);
    }

    /**
     * Iterables: remove all the items matching the condition
     *
     * @param itr
     * @param condition
     * @param <T>
     * @return
     */
    public <T> boolean remove(Iterable<T> itr, Predicate<T> condition) {
        return Iterables.removeIf(itr, condition);
    }

    /**
     * Iterators: remove all the items matching the condition
     *
     * @param itr
     * @param condition
     * @param <E>
     * @return
     */
    public <E> boolean remove(Iterator<E> itr, Predicate<E> condition) {
        return Iterators.removeIf(itr, condition);
    }

    /**
     * Iterators: all items matches the conditions
     *
     * @param itr
     * @param condition
     * @param <E>
     * @return
     */
    public <E> boolean matchAll(Iterator<E> itr, Predicate<E> condition) {
        return Iterators.all(itr, condition);
    }

    /**
     * Iterables: all items matches the conditions
     *
     * @param itr
     * @param condition
     * @param <T>
     * @return
     */
    public <T> boolean matchAll(Iterable<T> itr, Predicate<T> condition) {
        return Iterables.all(itr, condition);
    }

    /**
     * Iterators: any items matches the conditions
     *
     * @param itr
     * @param condition
     * @param <E>
     * @return
     */
    public <E> boolean matchAny(Iterator<E> itr, Predicate<E> condition) {
        return Iterators.any(itr, condition);
    }

    /**
     * Iterables: any items matches the conditions
     *
     * @param itr
     * @param condition
     * @param <T>
     * @return
     */
    public <T> boolean matchAny(Iterable<T> itr, Predicate<T> condition) {
        return Iterables.any(itr, condition);
    }
}
