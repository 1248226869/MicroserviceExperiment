package com.tailen.microservice.config.jwt;

import com.auth0.jwt.interfaces.Claim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zhao tailen
 * @description
 * @date 2019-05-06
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JWT jwt;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JwtException {
        final String authHeader = request.getHeader("authorization");
        log.info("authHeader is {}",authHeader);
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        } else {
            if (null == authHeader  ) {
                throw new JwtException("JWT校验失败");

            }
        }
        try {
            Claim  claims = JwtHelper.verifyToken(authHeader);
            if (claims == null) {
                throw new JwtException("JWT校验失败");
            }
            request.setAttribute("CLAIMS", claims);
        }catch (Exception e){
            throw new JwtException("JWT校验失败");
        }

        return true;
    }

}
