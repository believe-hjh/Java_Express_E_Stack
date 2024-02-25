package com.yemage.controller.wx;

import com.yemage.bean.BootstrapExpress;
import com.yemage.bean.Express;
import com.yemage.bean.Message;
import com.yemage.bean.User;
import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.service.ExpressService;
import com.yemage.util.DateFormatUtil;
import com.yemage.util.JSONUtil;
import com.yemage.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author yemage
 */
public class ExpressController {

    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findExpressByUserPhone(HttpServletRequest request, HttpServletResponse response) {

        User wxUser = UserUtil.getWxUser(request.getSession());
        String userPhone = wxUser.getUserPhone();
        List<Express> list = ExpressService.findByUserPhone(userPhone);

        List<BootstrapExpress> list2 = new ArrayList<>();

        for(Express e:list){
            String code = e.getCode() == null?"已取件":e.getCode();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            BootstrapExpress e2 = new BootstrapExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        Message message = new Message();
        if(list.size() == 0){

            message.setStatus(-1);

        }else {
            message.setStatus(0);
            Stream<BootstrapExpress> expressStream1 = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });
            Stream<BootstrapExpress> expressStream2 = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);

            });
            Object[] array1 = expressStream1.toArray();
            Object[] array2 = expressStream2.toArray();
            Map data = new HashMap();
            data.put("status1",array1);
            data.put("status2",array2);
            message.setData(data);
        }
        String json = JSONUtil.toJSON(message.getData());
        return json;
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootstrapExpress> list2 = new ArrayList<>();

        for(Express e:list){
            String code = e.getCode() == null?"已取件":e.getCode();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            BootstrapExpress e2 = new BootstrapExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        Message message = new Message();
        if(list.size() == 0){
            message.setStatus(-1);
            message.setResult("未查询到的快递");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(list2);
        }
        return JSONUtil.toJSON(message);
    }
}