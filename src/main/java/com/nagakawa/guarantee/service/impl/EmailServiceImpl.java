package com.nagakawa.guarantee.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.nagakawa.guarantee.configuration.OtpProperties;
import com.nagakawa.guarantee.model.ContentTemplate;
import com.nagakawa.guarantee.repository.ContentTemplateRepository;
import com.nagakawa.guarantee.service.EmailService;
import com.nagakawa.guarantee.util.Constants;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	private final ContentTemplateRepository contentTemplateRepository;
	
	private final OtpProperties otpProperties;

	@Value("${spring.mail.username}")
	private String from;

	private void sendEmail(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		javaMailSender.send(simpleMailMessage);
	}

	@Override
	public void sendResetPasswordEmail(String to, String otp, int duration) {
		ContentTemplate otpTemplate = contentTemplateRepository
				.findContentTemplateByTemplateCodeAndStatus(Constants.ContentTemplate.FORGOT_PWD, Constants.EntityStatus.ACTIVE);

		String template = String.format(otpTemplate.getTemplate(), otp, duration);

		this.sendEmail(to, otpTemplate.getTitle(), template);
	}

//	@Override
//	public void sendVerifyAccountEmail(String to, String otp, int duration) {
//		ContentTemplate otpTemplate = contentTemplateRepository
//				.findContentTemplateByTemplateCodeAndStatus(Constants.ContentTemplate.VERIFY_ORG_ACCOUNT, Constants.EntityStatus.ACTIVE);
//		
//		String link = String.format(otpProperties.getVerifyOrgAccountLinkTemplate(), otp);
//		
//		String template = String.format(otpTemplate.getTemplate(), link, duration);
//		
//		this.sendEmail(to, otpTemplate.getTitle(), template);
//	}
}
