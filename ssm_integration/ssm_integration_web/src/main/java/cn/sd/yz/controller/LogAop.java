package cn.sd.yz.controller;

import cn.sd.yz.domain.SysLog;
import cn.sd.yz.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService sysLogService;
    
    private Date visitTime;
    private Class clazz;
    private Method method;


    //前置通知 获取开始时间，执行的类是哪一个，执行的是哪个方法
    @Before("execution(* cn.sd.yz.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime = new Date(); //开始时间
        clazz = joinPoint.getTarget().getClass();  //执行的类
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            method = clazz.getMethod(methodName);
        }else{
            Class[] classArgs = new Class[args.length];
            for(int i=0;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            method = clazz.getMethod(methodName,classArgs);
        }

        System.out.println(method.getName());
    }


    //后置通知
    @After("execution(* cn.sd.yz.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        //获取执行的时长
        Long executionTime = new Date().getTime() - visitTime.getTime();

        //获取ip
        String ip = request.getRemoteAddr();

        //获取url
        String url = "";
        if(clazz!=null&&method!=null&&clazz!=LogAop.class){
            //获取类上的注解
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //获取方法上的注解
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url= classValue[0]+methodValue[0];
                }
            }
        }

        //获取操作者
        // 可以通过securityContext获取，也可以从request.getSession中获取
        SecurityContext context = SecurityContextHolder.getContext();
        //request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();


        //将日志信息封装到SysLog中
        SysLog sysLog = new SysLog();
        sysLog.setVisitTime(visitTime);
        sysLog.setUsername(username);
        sysLog.setIp(ip);
        sysLog.setUrl(url);
        sysLog.setExecutionTime(executionTime);
        sysLog.setMethod("[类名]" + clazz.getName() + "[方法名]" +method.getName());

        //保存到数据库中
        sysLogService.save(sysLog);

    }
}
