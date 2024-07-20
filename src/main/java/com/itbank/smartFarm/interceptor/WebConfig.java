package com.itbank.smartFarm.interceptor;

import com.itbank.smartFarm.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private NoticeInterceptor noticeInterceptor;

    @Autowired
    private SecretInterceptor secretInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/member/myPage",
                        "/member/update",
                        "/board/freemarket_write",
                        "/board/fBadd",
                        "/board/QnAadd",
                        "/board/freemarket_view/**",
                        "/chat/**",
                        "/pay/order",
                        "/pay/orderPrepare",
                        "/download");

        registry.addInterceptor(noticeInterceptor)
                .addPathPatterns("/board/notice_write");

        registry.addInterceptor(secretInterceptor)
                .addPathPatterns("/board/QnA_view/**");
    }
}
