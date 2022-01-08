package com.henry.shiroandjwt.filter;


import com.henry.shiroandjwt.common.CommonResultStatus;
import com.henry.shiroandjwt.exception.BusinessException;
import com.henry.shiroandjwt.jwt.JwtToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: JwtFilter
 * @Description: jwt过滤器
 * @Date: 2020/11/24
 */
public class JwtFilter extends AccessControlFilter {

    protected static final String AUTHORIZATION_HEADER = "Access-Token";

    /**
     *  表示是否允许访问，mappedValue就是[urls]配置中拦截器参数部分，
     *  如果允许访问返回true，否则false
     * @author henry
     * @date 2020/11/24
     * @param request:
      * @param response:
      * @param mappedValue:
     * @return: boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     *  表示当访问拒绝时是否已经处理了，
     *  如果返回true表示需要继续处理，
     *  如果返回false表示该拦截器实例已经处理了，将直接返回即可
     * @author henry
     * @date 2020/11/24
     * @param request:
      * @param response:
     * @return: boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        // 解决跨域问题
        if(HttpMethod.OPTIONS.toString().matches(req.getMethod())) {
            return true;
        }
        if (isLoginAttempt(request, response)) {
            //生成jwt token
            JwtToken token = new JwtToken(req.getHeader(AUTHORIZATION_HEADER));
            //委托给Realm进行验证
            try {
                //调用登陆会走Realm中的身份验证方法
                getSubject(request, response).login(token);
                return true;
            } catch (Exception e) {
            }
        }else{
            throw new BusinessException(CommonResultStatus.LOGIN_ERROR);
        }
        return false;
    }



    /**
     *  判断是否有头部参数
     * @author henry
     * @date 2020/11/24
     * @param request:
      * @param response:
     * @return: boolean
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        return authorization != null;
    }

}
