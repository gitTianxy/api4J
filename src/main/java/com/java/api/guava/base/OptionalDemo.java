package com.java.api.guava.base;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 * optional作用
 * 通过持有一个对象的引用来进行取值,存在判断,缺省值设置等操作
 * <p>
 * content
 * 1. optional实例创建: .of(), .absent(), .fromNullable()
 * 2. optional引用对象操作: .get(), .or(T), .orNull(); .isPresent()
 * <p>
 * 关于null处理的一些tips:
 * 1a. Set: 不要在Set中使用null, 因为和null没办法比较
 * 1b. Map: 不要把null作为map的键值, 因为会和map.get(key)=null混淆.
 * 1c. List: 如果你需要在列表中使用null, 并且这个列表的数据是稀疏的，使用Map<Integer, E>可能会更高效，并且更准确地符合你的潜在需求。
 * 2. 使用特殊值代表null会让查找操作的语义更清晰。举例来说，为某个enum类型增加特殊的枚举值表示null，比如java.math
 * .RoundingMode就定义了一个枚举值UNNECESSARY，它表示一种不做任何舍入操作的模式，用这种模式做舍入操作会直接抛出异常。
 * <p>
 * Created by kevintian on 2017/10/17.
 */
public class OptionalDemo {
    public static void main(String[] args) {
        Integer[] nums = {1, 2, null, 4, 5};
        for (Integer num : nums) {
            Optional<Integer> numOpt;
            try {
                numOpt = Optional.of(num);// Optional.of(T): T is not nullable
            } catch (NullPointerException e) {
                numOpt = Optional.absent();// Optional.absent(): 创建引用为空的Optional实例(单例方法)
            }
            if (numOpt.isPresent()) {// numOpt.isPresent()
                System.out.println(numOpt.get());// numOpt.get()
            } else {
                // numOpt.or(T): not present是返回指定值T; numOpt.orNull(): null present时返回null
                System.out.println(String.format("%s(%s)", numOpt.or(-1), numOpt.orNull()));
            }
        }
        System.out.println(StringUtils.repeat("-", 50));
        for (Integer num : nums) {
            Optional<Integer> numOpt = Optional.fromNullable(num);//Optional.fromNullable(T): T is nullable
            if (numOpt.isPresent()) {
                System.out.println(numOpt.get());
            } else {
                System.out.println(String.format("%s(%s)", numOpt.or(-1), numOpt.orNull()));
            }
        }
    }
}
