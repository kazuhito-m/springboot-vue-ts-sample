package com.github.kazuhito_m.mysample.presentation.requeststock;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * HttpServletがよばれるたびに、Requestを記憶するフィルタ。
 * <p>
 * 記憶するライフサイクルは「このフィルタの最初に記録、最後に破棄」。
 */
@Component
public class RequestStockFilter implements Filter {
    private final RequestStocks requestStocks;

    public RequestStockFilter(RequestStocks requestStocks) {
        this.requestStocks = requestStocks;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request == null || !(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        requestStocks.put(httpServletRequest);
        try {
            chain.doFilter(request, response);
        } finally {
            requestStocks.remove(httpServletRequest);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}