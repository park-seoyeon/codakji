package com.ssafy.codackji.model.service;

public interface EmailService {
	boolean sendEmail(String toAddress) throws Exception;
	boolean updateCertification(String email) throws Exception;
	boolean sendPassword(String email, String password) throws Exception;
}
