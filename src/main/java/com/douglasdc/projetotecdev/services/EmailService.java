package com.douglasdc.projetotecdev.services;

import org.springframework.mail.SimpleMailMessage;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

public interface EmailService {
	
	void sendOrderOrcamento(OrdemDeServico obj);

	void sendEmail(SimpleMailMessage msg) ;
		
}
