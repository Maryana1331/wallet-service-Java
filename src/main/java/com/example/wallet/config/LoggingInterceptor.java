package com.example.wallet.config;

import com.example.wallet.config.Log;
import com.example.wallet.config.LogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {

    private final LogRepository logRepository;

    public LoggingInterceptor(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Log logEntity = new Log();
        logEntity.setMethod(request.getMethod());
        logEntity.setPath(request.getRequestURI());
        logEntity.setIpAddress(request.getRemoteAddr());
        logEntity.setTimestamp(LocalDateTime.now());
        logRepository.save(logEntity);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            Log logEntity = new Log();
            logEntity.setStatus(response.getStatus());
            logEntity.setErrorMessage(ex.getMessage() != null ? ex.getMessage() : "Нет сообщения");
            logRepository.save(logEntity);
        }
    }
}
