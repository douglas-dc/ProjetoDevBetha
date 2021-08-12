package com.douglasdc.projetotecdev.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.repositories.FuncionarioRepository;
import com.douglasdc.projetotecdev.resources.exceptions.FieldMessage;

public class FuncionarioInsertValidator implements ConstraintValidator<FuncionarioInsert, Funcionario> {
	
	@Autowired
	private FuncionarioRepository repo;
	
	@Override
	public void initialize(FuncionarioInsert ann) {
	}

	@Override
	public boolean isValid(Funcionario obj, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (repo != null) {
			Funcionario aux = repo.findByEmail(obj.getEmail());
			if (aux != null) {
				list.add(new FieldMessage("email", "Email já existente"));
			}
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
