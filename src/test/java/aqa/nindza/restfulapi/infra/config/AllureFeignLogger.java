package aqa.nindza.restfulapi.infra.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j // Эта аннотация создаст поле 'log' для вывода в консоль
public class AllureFeignLogger extends Logger {

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append(String.format("Method: %s\n", request.httpMethod()));
        requestLog.append(String.format("URL: %s\n", request.url()));
        requestLog.append("Headers:\n");
        request.headers().forEach((key, values) -> requestLog.append(String.format("  %s: %s\n", key, values)));

        if (request.body() != null) {
            requestLog.append(String.format("\nBody:\n%s\n", new String(request.body(), request.charset())));
        }

        String finalLog = requestLog.toString();

        // 1. Дублируем в консоль IDEA (подсветится как красивый INFO лог)
        log.info("\n--- >>> FORWARD HTTP REQUEST >>> ---\n{}", finalLog);

        // 2. Отправляем в Allure
        attachRequest(finalLog);

        super.logRequest(configKey, logLevel, request);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        byte[] bodyData = response.body() != null ? response.body().asInputStream().readAllBytes() : new byte[0];
        Response rebufferedResponse = response.toBuilder().body(bodyData).build();

        StringBuilder responseLog = new StringBuilder();
        responseLog.append(String.format("Status: %s\n", response.status()));
        responseLog.append(String.format("Time: %sms\n", elapsedTime));
        responseLog.append("Headers:\n");
        response.headers().forEach((key, values) -> responseLog.append(String.format("  %s: %s\n", key, values)));

        if (bodyData.length > 0) {
            responseLog.append(String.format("\nBody:\n%s\n", new String(bodyData)));
        }

        String finalLog = responseLog.toString();

        // 1. Дублируем в консоль IDEA
        log.info("\n--- <<< BACKWARD HTTP RESPONSE <<< ---\n{}", finalLog);

        // 2. Отправляем в Allure
        attachResponse(finalLog);

        return super.logAndRebufferResponse(configKey, logLevel, rebufferedResponse, elapsedTime);
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        // Оставляем пустым, так как мы полностью переопределили логирование выше
    }

    @Attachment(value = "HTTP Request", type = "text/plain")
    public String attachRequest(String log) {
        return log;
    }

    @Attachment(value = "HTTP Response", type = "text/plain")
    public String attachResponse(String log) {
        return log;
    }
}
