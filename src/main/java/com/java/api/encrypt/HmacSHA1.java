package com.java.api.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kevintian on 2017/9/28.
 */
public class HmacSHA1 {
    public static void main(String[] args) {
        String key = "ppc";
        String content = "snoss";
        byte[] sign = getHmacSHA1(key, content);
        System.out.println("hex encode:" + new String(Hex.encodeHex(sign)));
        System.out.println("base64 encode:" + Base64.encodeBase64URLSafeString(sign));
    }

    private static byte[] getHmacSHA1(String key, String content) {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
        } catch (Exception e) {//should never occur
            throw new IllegalStateException("unexpected", e);
        }
        return mac.doFinal(content.getBytes());
    }
}
