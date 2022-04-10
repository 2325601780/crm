package com.crm.settings.web.controller;

import com.crm.exception.LoginException;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.MD5Util;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import org.apache.ibatis.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("用户控制器");

        String path = request.getServletPath();

        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if("/settings/user/xxx.do".equals(path)){

        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("登陆验证");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        String ip = request.getRemoteAddr();

        boolean flag = true;

        String loginCookie = request.getParameter("loginCookie");
        if("true".equals(loginCookie)){
            Cookie[] cookies = request.getCookies();
            String loginActC = null;
            String loginPwdC = null;
            if(cookies!=null){
                for(Cookie cookie : cookies){
                    String name = cookie.getName();
                    if("loginActC".equals(name)){
                        loginActC = cookie.getValue();
                    }else if("loginPwdC".equals(name)){
                        loginPwdC = cookie.getValue();
                    }
                }
            }

            if(loginActC!=null && loginPwdC!=null){
                try {
                    System.out.println("login");
                    User user = us.login(loginActC,loginPwdC,ip);
                    request.getSession().setAttribute("user",user);
                    flag = false;
                    System.out.println("免登录");
                    PrintJson.printJsonFlag(response,true);
                } catch (LoginException e) {

                }
            }
        }




        if (flag){
            String loginAct = request.getParameter("loginAct");
            String loginPwd = request.getParameter("loginPwd");

            if(loginCookie.length()!=32){
                loginPwd = MD5Util.getMD5(loginPwd);
            }
            System.out.println("========ip"+ip);


            try{

                User user = us.login(loginAct,loginPwd,ip);

                if("true".equals(loginCookie)){
                    System.out.println("开始存入");
                    Cookie cookie1 = new Cookie("loginActC",user.getLoginAct());
                    Cookie cookie2 = new Cookie("loginPwdC",user.getLoginPwd());
                    cookie1.setMaxAge(60*60*24*10);
                    cookie2.setMaxAge(60*60*24*10);
                    cookie1.setPath("/");
                    cookie2.setPath("/");
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                    System.out.println("存入成功");
                }

                request.getSession().setAttribute("user",user);
                PrintJson.printJsonFlag(response,true);

            }catch(LoginException e){
                e.printStackTrace();
                String msg = e.getMessage();

                Map<String,Object> map = new HashMap<>();
                map.put("success",false);
                map.put("msg",msg);

                PrintJson.printJsonObj(response,map);
            }
        }

    }
}
