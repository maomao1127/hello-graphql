package com.example.hellographql.common.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 统一处理resolver层的入参出参并打印至log中
 * 要求所有的dto重写toString()
 */

// @Aspect
// @Component
public class CommonLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(CommonLogAspect.class);

    @Pointcut("execution(public * com.example.hellographql.*.graphql.resolver.*.*(..))")
    public void resolverLogPointcut() {
    }

    @Around("resolverLogPointcut()")
    public Object resolverLog(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userId = (String) request.getAttribute("userId");
        String name = pjp.getSignature().getName();
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();
        JSONObject params = this.getAllRequestParams(request);
        JSONObject dtoJson = new JSONObject();
        try {
            Object[] args = pjp.getArgs();
            String[] names = ((MethodSignature) pjp.getSignature()).getParameterNames();
            Class[] types = ((MethodSignature) pjp.getSignature()).getParameterTypes();
            for (int i = 0; i < args.length; i++) {
                if (!("HttpServletRequest").equals(types[i].getSimpleName())
                        && !params.containsKey(names[i])) {
                    dtoJson.put(names[i], args[i] == null ? null : args[i].toString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("{}.request#{\"uri\":{}, \"httpMethod\":{}, \"userId\":{}, \"param\":{}, \"data\":{}}", name, uri, httpMethod, userId, JSONObject.toJSONString(params)
                , dtoJson);
        Object ret = pjp.proceed();
        logger.info("{}.response#{}", name, ret);
        logger.info("{}.time-cost#:<{}>ms", name, (System.currentTimeMillis() - begin));
        return ret;
    }

    private JSONObject getAllRequestParams(HttpServletRequest request) {
        JSONObject param = new JSONObject();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            param.put(paraName, request.getParameter(paraName));
        }
        return param;
    }

}