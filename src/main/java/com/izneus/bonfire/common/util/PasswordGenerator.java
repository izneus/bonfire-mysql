package com.izneus.bonfire.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 密码生成器，留给一些临时需要手动看明文或者密文的场景使用
 *
 * @author Izneus
 * @date 2022-01-06
 */
@Slf4j
public class PasswordGenerator {
    public static void main(String[] args) {
        String plaintext = "123456";
        String ciphertext = CommonUtil.encryptPassword(plaintext);
        log.info("{}的密文 = {}", plaintext, ciphertext);
    }
}
