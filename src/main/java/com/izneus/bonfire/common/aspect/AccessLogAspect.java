package com.izneus.bonfire.common.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.util.HttpContextUtil;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import com.izneus.bonfire.module.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * api访问日志切面，注意不要和slf4j等日志系统混淆
 *
 * @author Izneus
 * @date 2020/08/06
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AccessLogAspect {

    private final SysAccessLogService accessLogService;
    private final SysUserService userService;

    private ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.izneus.bonfire.common.annotation.AccessLog)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 开始时间
        currentTime.set(System.currentTimeMillis());
        // 先执行业务
        Object result = point.proceed();
        // 处理join point
        handlePoint(point);
        return result;
    }

    private void handlePoint(ProceedingJoinPoint point) {
        // 计算请求消耗时长
        long elapsedTime = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        AccessLog annotation = method.getAnnotation(AccessLog.class);
        // 方法路径
        String className = point.getTarget().getClass().getName();
        String funcName = signature.getName();
        String methodName = className + "." + funcName + "()";
        // 请求参数，序列化为json字符串
        Object[] argValues = point.getArgs();
        JSONArray argJson = JSONUtil.parseArray(argValues);
        String param = null;
        // 注意数据脱敏，特别是用户密码
        if (argJson.size() > 0) {
            String loginMethod = "com.izneus.bonfire.module.system.controller.v1.LoginController.login()";
            if (loginMethod.equals(methodName)) {
                ((JSONObject) argJson.get(0)).set("password", "*");
            }
            param = argJson.toString();
        }
        // 注解描述
        String description = annotation.value();
        // 获取request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 客户端ip
        String clientIp = ServletUtil.getClientIP(request);
        // ua和解析出的浏览器和系统
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        String browser = ua.getBrowser().toString();
        String os = ua.getOs().toString();
        // 写库
        SysAccessLogEntity accessLogEntity = new SysAccessLogEntity();
        accessLogEntity.setMethod(methodName);
        accessLogEntity.setParam(param);
        accessLogEntity.setDescription(description);
        accessLogEntity.setClientIp(clientIp);
        accessLogEntity.setBrowser(browser);
        accessLogEntity.setOs(os);
        accessLogEntity.setUserAgent(request.getHeader("User-Agent"));
        accessLogEntity.setElapsedTime(elapsedTime);
        // 获取用户名
        String userId = CurrentUserUtil.getFillUserId();
        if (userId != null) {
            SysUserEntity user = userService.getById(userId);
            accessLogEntity.setUsername(user.getUsername());
        }
        accessLogService.save(accessLogEntity);
    }
}
