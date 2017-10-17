package com.java.api.guava.base;

import com.google.common.base.Preconditions;

/**
 * TIPS:
 * 有checkArgument, checkNotNull, checkElementIndex等方法, 每个方法都有3种入参形式:
 * 1. expression
 * 2. expression, errorMsg
 * 3. expression, errorMsgTpl, errorMsgArgs
 *
 * Created by kevintian on 2017/10/17.
 */
public class PreconditionDemo {

    /**
     * I. check true
     *
     * @param expression
     */
    public void checkTrue(boolean expression) {
        Preconditions.checkArgument(expression);
    }

    /**
     * I. check true
     *
     * @param expression
     * @param errorMessage
     */
    public void checkTrue(boolean expression, Object errorMessage) {
        Preconditions.checkArgument(expression, errorMessage);
    }

    /**
     * I. check true
     *
     * @param expression
     * @param errorMsgTpl
     * @param errorMsgArgs
     */
    public void checkTrue(boolean expression, String errorMsgTpl, Object ... errorMsgArgs) {
        Preconditions.checkArgument(expression, errorMsgTpl, errorMsgArgs);
    }

    /**
     * II. check not null
     *
     * @param obj
     * @param errMsgTpl
     * @param errMsgArgs
     * @param <T>
     * @return
     */
    public <T> T checkNotNull(T obj, String errMsgTpl, Object ... errMsgArgs) {
        return Preconditions.checkNotNull(obj, errMsgTpl, errMsgArgs);
    }

    /**
     * check element index
     *
     * @param idx
     * @param size
     * @param desc description for the error
     * @return
     */
    public int checkElementIdx(int idx, int size, String desc) {
        return Preconditions.checkElementIndex(idx, size, desc);
    }
}
