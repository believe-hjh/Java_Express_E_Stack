package com.yemage.service;

import com.yemage.bean.Express;
import com.yemage.dao.BaseExpressDao;
import com.yemage.dao.impl.ExpressDaoImple;
import com.yemage.exception.DuplicateCodeException;
import com.yemage.util.RandomUtil;
import com.yemage.util.SMSUtil;

import java.util.List;
import java.util.Map;

/**
 * @author yemage
 */
public class ExpressService{

    private static BaseExpressDao expressDao = new ExpressDaoImple();

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size：总数，day：新增}，{size:总数，day:新增}]
     */

    public static List<Map<String, Integer>> console() {

        return expressDao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的表级，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return expressDao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 快递单号
     * @return 查询的快递信息，单号不存在时，返回null
     */

    public static Express findByNumber(String number) {
        return expressDao.findByNumber(number);
    }

    /**
     * 根据取件码慢查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */

    public static Express findByCode(String code) {
        return expressDao.findByCode(code);
    }

    /**
     * 根据用户手机号码查询快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findByUserPhone(String userPhone) {
        return expressDao.findByUserPhone(userPhone);
    }

    /**
     * 根据用户手机号码查询快递信息
     *
     * @param userPhone 手机号码
     * @param status
     * @return 查询的快递信息列表
     */

    public static List<Express> findByUserPhoneAndStatus(String userPhone,int status) {
        return expressDao.findByUserPhoneAndStatus(userPhone,status);
    }

    /**
     * 根据录入人的手机号，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findBySysPhone(String sysPhone) {
        return expressDao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param express 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */

    public static boolean insert(Express express){
        //1.生成取件码
        express.setCode(RandomUtil.getCode() + "");
        try {
            boolean flag = expressDao.insert(express);
            if(flag){
                //录入成功
                SMSUtil.send(express.getUserPhone(),express.getCode());
            }
            return flag;
        } catch (DuplicateCodeException e) {
            return insert(express);
        }

    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number，company，username，userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     */

    public static boolean update(int id, Express newExpress) {
        if(newExpress.getUserPhone() != null){
            expressDao.delete(id);
           return insert(newExpress);

        }else{
            boolean update = expressDao.update(id, newExpress);
            Express e = expressDao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }

    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果，true表示成功，false表示失败
     */

    public static boolean updateStatus(String code) {
        return expressDao.updateStatus(code);
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递的id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return expressDao.delete(id);
    }
}
