package com.github.kazuhito_m.mysample.presentation.requeststock;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * HttpServletに指定されたRequestオブジェクトの一時的な保管場所。
 * <p>
 * Servletは呼び出しごとにThreadが起こることを利用し、
 * 内部は「Thread#getId()をKeyにしたMap」で保存している。
 * (このクラスのget()メソッドに至るStackFrame中で「２度も３度もServletを呼んでいない」ことを前提としている)
 */
@Component
public class RequestStocks {
    private final Map<Long, HttpServletRequest> requests = new HashMap<>();

    public void put(HttpServletRequest request) {
        modify(request, false);
    }

    public void remove(HttpServletRequest request) {
        modify(request, true);
    }

    private synchronized void modify(HttpServletRequest request, boolean delete) {
        long threadId = Thread.currentThread().getId();
        if (delete) {
            requests.remove(threadId);
        } else {
            requests.put(threadId, request);
        }
    }

    public Optional<HttpServletRequest> get() {
        long threadId = Thread.currentThread().getId();
        return Optional.of(requests.get(threadId));
    }

    public int size() {
        return requests.size();
    }
}
