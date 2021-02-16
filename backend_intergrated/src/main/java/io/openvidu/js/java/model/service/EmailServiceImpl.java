package io.openvidu.js.java.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

import io.openvidu.js.java.model.mapper.EmailMapper;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	public boolean sendEmail(String toAddress) throws Exception {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String title = "코딱지 회원가입을 축하합니다!";

		String htmlStr = "<div style='text-align: center'>"

				+ "<h1>안녕하세요! 코딱지 회원가입을 축하드립니다:)</h1>" + "<h2>아래 인증 버튼을 통해 이메일 인증을 완료하세요.</h2>"
				+ "<a href='https://localhost:8000/certify/" + toAddress + "' style='text-decoration:none'>"
				//+ "<a href='https://i4a203.p.ssafy.io:8000/certify/" + toAddress + "' style='text-decoration:none'>"
				+ "<img src='https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7AVpo%2FbtqUM75u9KM%2F6a6GmXy384zwyq8sdZgLQK%2Fimg.png'/>"
				+ "<h2>인증하기</h2>" + "</a></div>";

		/*
		 * Context context = new Context(); context.setVariable("email", toAddress);
		 * String htmlStr = templateEngine.process("mail-template", context);
		 */
		try {
			helper.setTo(toAddress);
			helper.setSubject(title);
			helper.setText(htmlStr, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		sender.send(message);

		return true;
	}

	@Override
	public boolean updateCertification(String email) throws Exception {
		return sqlSession.getMapper(EmailMapper.class).updateCertification(email) == true;
	}

	@Override
	public boolean sendPassword(String email, String password) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String title = "코딱지 임시 비밀번호를 발급해 드립니다.";

		String htmlStr = "<div style='text-align: center'>"
				+ "<h1>코딱지 임시 비밀번호는 아래와 같습니다:)</h1>" 
				+ "<h2 style='color:lightblue'>로그인 후 비밀번호 변경 부탁드립니다.</h2>" 
				+ "<img src='https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7AVpo%2FbtqUM75u9KM%2F6a6GmXy384zwyq8sdZgLQK%2Fimg.png'/>"
				+ "<h2 style='color:blue'>임시 비밀번호: " + password + "</h2></div>";
		
		try {
			helper.setTo(email);
			helper.setSubject(title);
			helper.setText(htmlStr, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		sender.send(message);

		return true;
	}

}