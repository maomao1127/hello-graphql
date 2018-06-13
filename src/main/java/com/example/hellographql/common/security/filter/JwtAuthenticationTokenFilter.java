package com.example.hellographql.common.security.filter;

import com.alibaba.fastjson.JSON;
import com.example.hellographql.common.PetClinicGraphQLError;
import com.example.hellographql.common.security.service.JwtTokenService;
import graphql.GraphQLError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenservice;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        logger.debug("enter filter");
        String authHeader = request.getHeader(this.tokenHeader);

        String url = request.getRequestURI();
        if (authHeader != null && authHeader.startsWith(tokenPrefix)) {
            final String authToken = authHeader.substring(tokenPrefix.length()); // The part after "Bearer "
            try {
                jwtTokenservice.verifyJwt(authToken);
            } catch (Exception e) {
                // BaseResponse<String> baseResponse = new BaseResponse<>();
                // baseResponse.setCode(GlobalErrorCode.ERROR_CODE_99);
                // baseResponse.setMsg(GlobalErrorCode.ERROR_MSG_99);
                // String jsonString = JSON.toJSONString(baseResponse);
                List<GraphQLError> errors = new ArrayList<>();
                PetClinicGraphQLError error = new PetClinicGraphQLError(99, "鉴权失败");
                errors.add(error);
                Object data = null;
                response.setStatus(200);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter out = response.getWriter();
                out.println(JSON.toJSONString(this.createResultFromDataAndErrors(data, errors)));
                out.close();
                return;
            }
            String userId = null;
            String userRole = null;
            try {
                userId = jwtTokenservice.getUserIdFromToken(authToken);
                request.setAttribute("userId", userId);
                userRole = jwtTokenservice.getRoleFromToken(authToken);
                logger.debug("userId = {} , userRole = {}", userId, userRole);
            } catch (Exception e) {
                logger.error("从jwttoken中取userId失败，失败原因:{}", e.getMessage());
            }

            if (userId != null && !"".equals(userId)) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
                Collection authorityCollection = userDetails.getAuthorities();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
                authorityCollection.add(authority);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorityCollection);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                logger.info("authenticated user " + userId + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private Map<String, Object> createResultFromDataAndErrors(Object data, List<GraphQLError> errors) {
        Map<String, Object> result = new HashMap();
        result.put("data", data);
        result.put("errors", errors);
        return result;
    }
}
