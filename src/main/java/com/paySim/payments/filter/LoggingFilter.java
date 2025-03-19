package com.paySim.payments.filter;


import com.paySim.payments.domain.LogEntry;
import com.paySim.payments.repository.LogEntryRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoggingFilter implements Filter {

    private final com.paySim.payments.repository.LogEntryRepository logEntryRepository;

    public LoggingFilter(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean responseStatus = true;

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            responseStatus = false;
            throw e;
        } finally {
            String machineId = request.getRemoteHost();
            String endpoint = httpRequest.getRequestURI();
            LocalDateTime timestamp = LocalDateTime.now();

            LogEntry logEntry = new LogEntry();
            logEntry.setMachineId(machineId);
            logEntry.setTimestamp(timestamp);
            logEntry.setEndpoint(endpoint);
            logEntry.setResponseStatus(responseStatus);
            logEntryRepository.save(logEntry);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}