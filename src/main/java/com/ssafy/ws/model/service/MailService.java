package com.ssafy.ws.model.service;

import java.util.UUID;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender mailSender;

	public String sendVerificationEmail(String email) {
		String authCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setSubject("[MyRoute] 이메일 인증 코드 안내");
			helper.setText("인증 코드: " + authCode + "\n\n인증번호 확인란에 기입하여 주세요.");
			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("메일 전송 실패", e);
		}
		return authCode;
	}
}
