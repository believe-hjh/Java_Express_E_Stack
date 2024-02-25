package com.yemage.controller.wx;

import com.yemage.bean.BootstrapExpress;
import com.yemage.bean.Express;
import com.yemage.bean.Message;
import com.yemage.bean.User;
import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.mvc.annotation.ResponseView;
import com.yemage.service.ExpressService;
import com.yemage.util.DateFormatUtil;
import com.yemage.util.JSONUtil;
import com.yemage.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yemage
 */
public class QRCodeController {

    @ResponseView("/wx/createQRCode.do")
    public String createQrCode(HttpServletRequest request, HttpServletResponse response){

        String code = request.getParameter("code");
        //express |user
        String type = request.getParameter("type");
        String userPhone = null;
        String qRCodeContent = null;
        if("express".equals(type)){
            //快递二维码，被扫后，展示单个快递的信息
            //code
            qRCodeContent = "express_" + code;
        }else{
            //用户二维码：被扫后，快递员（柜子）端展示用户所有的快递
            //userPhone
            User wxUser = UserUtil.getWxUser(request.getSession());
            userPhone = wxUser.getUserPhone();
            qRCodeContent = "userPhone_" + userPhone ;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qRCodeContent);
        return "/personQRcode.html";

    }


    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");

        Message message = new Message();
        if(qrcode == null){
            message.setStatus(-1);
            message.setResult("取件码获取出错，请用户重新操作！");
        }else{
            message.setStatus(0);
            message.setResult(qrcode);
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        boolean status = ExpressService.updateStatus(code);
        Message message = new Message();
        if(status){
            message.setStatus(0);
            message.setResult("取件成功");
        }else{
            message.setStatus(-1);
            message.setResult("取件码不存在，请用户更新二维码");
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        Express express = ExpressService.findByCode(code);
        BootstrapExpress express2 = null;
        if(express != null){
            express2 = new BootstrapExpress(express.getId(),express.getNumber(), express.getUsername(), express.getUserPhone(),express.getCompany(), express.getCode(), DateFormatUtil.format(express.getInTime()),express.getOutTime()==null?"未出库":DateFormatUtil.format(express.getOutTime()), express.getStatus()==0?"待取件":"已取件",express.getSysPhone());
        }
        Message message = new Message();
        if(express == null){
            message.setStatus(-1);
            message.setResult("取件码不存在");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(express2);
        }
        return JSONUtil.toJSON(message);
    }
}
