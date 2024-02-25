package com.yemage.service;

import com.yemage.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author yemage
 */
public class ExpressServiceTest {

    @Test
    public void insert() {
        Express express = new Express("12333","野马哥","17636602606","顺丰快递","18888888888","1111111");
        ExpressService.insert(express);
    }
}