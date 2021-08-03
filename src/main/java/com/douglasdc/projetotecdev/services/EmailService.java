package com.douglasdc.projetotecdev.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

public interface EmailService {
	
	void sendOrderOrcamento(OrdemDeServico obj);

	void sendEmail(SimpleMailMessage msg) ;
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendOrderConfirmationHtmlEmail(OrdemDeServico obj);
	
}
