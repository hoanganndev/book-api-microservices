package com.marcus.notificationservice.service;

import com.marcus.notificationservice.dto.request.EmailRequest;
import com.marcus.notificationservice.dto.request.SendEmailRequest;
import com.marcus.notificationservice.dto.request.Sender;
import com.marcus.notificationservice.dto.response.EmailResponse;
import com.marcus.notificationservice.exception.AppException;
import com.marcus.notificationservice.exception.ErrorCode;
import com.marcus.notificationservice.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;

    EmailClient emailClient;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Marcus Solution")
                        .email("hoangann.dev@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();

        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }
}
