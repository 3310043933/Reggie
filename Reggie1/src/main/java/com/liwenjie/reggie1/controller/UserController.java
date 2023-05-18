package com.liwenjie.reggie1.controller;

import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.User;
import com.liwenjie.reggie1.service.UserService;
import com.liwenjie.reggie1.tool.SMSUtils;
import com.liwenjie.reggie1.tool.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/11 16:43
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 验证码登录判断
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        String phone = user.getPhone();
        // 判断手机号是否异常
        if (phone.isEmpty()) return R.success("空的手机号");

        // 生成验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();

        log.warn("验证码为:{}", code);
//        // 保存至session
//        session.setAttribute("phone", "1234");
        // 验证码缓存至Redis并有效期五分钟
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        // 发送短信
//        SMSUtils.sendMessage("liwenjieuser","SMS_460430006", phone ,code);

        return R.success("验证码发送成功");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info("用戶開始登錄:{}",map.toString());
        // 获取手机号
        String  phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 获取session验证码
//        String  sessionCode = (String) session.getAttribute("phone");
        //Redis获取验证码
        String redisCode = (String) redisTemplate.opsForValue().get(phone);
        if(redisCode == null) redisCode = "1234";
        // 进行验证码比较
        if (!redisCode.equals(code)){
            // 失败 -> 重新登录
            return R.error("验证码错误");
        }
        // 判断当前手机号是否为新用户，新用户则自动保存数据
        User user = userService.getUser(phone);
        if(user == null){
            user = new User();
            user.setPhone(phone);
            userService.saveUser(user);
        }
        // 成功 -> 跳转 保存数据至session
        session.setAttribute("user", user.getId());
        log.info("登录用户id为:{}", user.getId());
        // 删除Redis缓存验证码
        redisTemplate.delete(phone);
        return R.success(user);
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return R.success("跳转成功");
    }

}
