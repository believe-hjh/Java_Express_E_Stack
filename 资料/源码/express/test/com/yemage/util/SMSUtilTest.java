package com.yemage.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author yemage
 */
public class SMSUtilTest {

    @Test
    public void send() {
        boolean send = SMSUtil.send("17636602606", "123456");
        System.out.println(send);
    }

    @Test
    public void loginSMS() {
        boolean send = SMSUtil.loginSMS("17636602606", "111111");
        System.out.println(send);
    }
}