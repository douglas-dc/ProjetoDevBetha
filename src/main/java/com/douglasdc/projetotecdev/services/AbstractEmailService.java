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
	public void sendOrderConfirmation(OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrdemDeServico(obj);
		sendEmail(sm);
	}
	
	@Override
	public void sendOrderConclusion(OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrdemDeServicoConclusion(obj);
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
	
	protected SimpleMailMessage prepareSimpleMailMessageFromOrdemDeServicoConclusion(OrdemDeServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente());
		sm.setFrom(sender);
		sm.setSubject("Ordem de Serviço Finalizada" + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Informamos que a ordem de serviço número " + obj.getId() + " foi concluída. Para outras informações entre em contato.");
		return sm;
	}
	
	protected String htmlFromTemplateOrdemDeServico(OrdemDeServico obj) {
		Context context = new Context();
		context.setVariable("ordemdeservico", obj);
		context.setVariable("osAprovadaUrl", "http://localhost:8080/ordens/" + obj.getId() + "/aprovada");
		context.setVariable("osRecusadaUrl", "http://localhost:8080/ordens/" + obj.getId() + "/recusada");
		System.out.println(templateEngine.process("email/confirmacaoDeOrdem", context));
		return templateEngine.process("email/confirmacaoDeOrdem", context);
	}
	
	protected String htmlFromTemplateOrdemDeServicoConclusion(OrdemDeServico obj) {
		Context context = new Context();
		context.setVariable("ordemdeservico", obj);
		System.out.println(templateEngine.process("email/conclusaoDeOrdem", context));
		return templateEngine.process("email/conclusaoDeOrdem", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(OrdemDeServico obj) {
		try {
			MimeMessage mm = prepareMimeMailMessageFromOrdemDeServico(obj);
			sendHtmlEmail(mm);	
		}
		catch (MessagingException e) {
			sendOrderConfirmation(obj);
		}
	}
	
	@Override
	public void sendOrderConclusionHtmlEmail(OrdemDeServico obj) {
		try {
			MimeMessage mm = prepareMimeMailMessageFromOrdemDeServicoConclusion(obj);
			sendHtmlEmail(mm);	
		}
		catch (MessagingException e) {
			sendOrderConclusion(obj);
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
	
	protected MimeMessage prepareMimeMailMessageFromOrdemDeServicoConclusion(OrdemDeServico obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente());
		mmh.setFrom(sender);
		mmh.setSubject("Ordem de serviço finalizada!" + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateOrdemDeServicoConclusion(obj), true);
		return mimeMessage;
	}
}
