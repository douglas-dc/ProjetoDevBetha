package com.douglasdc.projetotecdev.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderOrcamento(OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrdemDeServico(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrdemDeServico(OrdemDeServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente());
		sm.setFrom(sender);
		sm.setSubject("Requisição de Serviço" + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
}
