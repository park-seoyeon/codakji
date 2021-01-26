package com.ssafy.codackji.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ssafy.codackji.model.mapper.EmailMapper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;




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
    
    
    String htmlStr = "<h1>안녕하세요</h1>"
    		+"<a href='http://localhost:8000/codackji/certify/" + toAddress + "'>인증하기</a>";
    
    
    //Context context = new Context();
    //String htmlStr = templateEngine.process("mail-template", context);
    
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

}