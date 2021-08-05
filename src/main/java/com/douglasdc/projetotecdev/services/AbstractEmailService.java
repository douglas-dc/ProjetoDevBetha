package com.douglasdc.projetotecdev.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

public abstract class AbstractEmailService implements EmailService{

	@Autowired
	private TemplateEngine templateEngine;
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplateOrdemDeServico(OrdemDeServico obj) {
		Context context = new Context();
		context.setVariable("ordemdeservico", obj);
		context.setVariable("osAprovadaUrl", "http://localhost:8080/ordens/" + obj.getId() + "/aprovada");
		context.setVariable("osReprovadaUrl", "http://localhost:8080/ordens/" + obj.getId() + "/reprovada");
		System.out.println(templateEngine.process("email/confirmacaoDeOrdem", context));
		return templateEngine.process("email/confirmacaoDeOrdem", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(OrdemDeServico obj) {
		try {
			MimeMessage mm = prepareMimeMailMessageFromOrdemDeServico(obj);
			sendHtmlEmail(mm);	
		}
		catch (MessagingException e) {
			sendOrderOrcamento(obj);
		}
	}

	protected MimeMessage prepareMimeMailMessageFromOrdemDeServico(OrdemDeServico obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente());
		mmh.setFrom(sender);
		mmh.setSubject("Ordem enviada!");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateOrdemDeServico(obj), true);
		return mimeMessage;
	}
}
