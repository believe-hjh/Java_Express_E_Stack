package com.yemage.controller.wx;

import com.yemage.bean.Message;
import com.yemage.bean.User;
import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.util.JSONUtil;
import com.yemage.util.RandomUtil;
import com.yemage.util.SMSUtil;
import com.yemage.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yemage
 */
public class UserController {

    @ResponseBody("/wx/loginSMS.do")
    public String sendSMS(HttpServletRequest request, HttpServletResponse response){

        String userPhone = request.getParameter("userPhone");
        //String code = RandomUtil.getCode() + "";
        //boolean flag = SMSUtil.loginSMS(userPhone, code);
        boolean flag = true;
        String code = "123456";
        Message message = new Message();
        if(flag){
            //短信发送发成功
            message.setStatus(0);
            message.setResult("验证码已发送，请查收！");
        }else{
            //短信发送失败
            message.setStatus(1);
            message.setResult("验证码下发失败，请检查手机号或稍后再试！");
        }
        UserUtil.setLoginSMS(request.getSession(),userPhone,code);
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){

        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSMS(request.getSession(), userPhone);
        Message message = new Message();
        if(sysCode ==null){
            //这个手机号码未获取短信
            message.setStatus(-1);
            message.setResult("手机号码未获取短信");
        }else if(sysCode.equals(userCode)){
            //这里手机号码和短信一致，表示登录成功
            //TODO 这个判断应替换为快递员表格查询手机号的结果
            User user = new User();
            if("17636602606".equals(userPhone)){
                //快递员
                message.setStatus(1);
                user.setUser(false);
            }else{
                //用户
                message.setStatus(0);
                user.setUser(true);
            }
            user.setUserPhone(userPhone);
            UserUtil.setWxUser(request.getSession(),user);

        }else {
            //这里是验证码不一致，登录失败
            message.setStatus(-2);
            message.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        User wxUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = wxUser.isUser();
        Message message = new Message();
        if(isUser){
            message.setStatus(0);
        }else {
            message.setStatus(1);
        }
        message.setResult(wxUser.getUserPhone());
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //1.销毁session
        request.getSession().invalidate();
        //2.给客户端回复消息
        Message message = new Message(0);
        return JSONUtil.toJSON(message);
    }
}
