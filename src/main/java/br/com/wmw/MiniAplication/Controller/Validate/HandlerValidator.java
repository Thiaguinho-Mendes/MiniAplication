package br.com.wmw.MiniAplication.Controller.Validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandlerValidator {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<CamposValidator> Handler(MethodArgumentNotValidException e) {
		List<CamposValidator> validator = new ArrayList<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		fieldErrors.forEach(erro -> {
			CamposValidator erros;
			if (erro.getDefaultMessage().contains("CNPJ") && erro.getRejectedValue().toString().length() <= 11) {
				return;
			} else if (erro.getDefaultMessage().contains("CPF") && erro.getRejectedValue().toString().length() > 11) {
				return;
			}
			erros = new CamposValidator(erro.getField(), erro.getDefaultMessage());
			validator.add(erros);
		});
		return validator;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	public CamposValidator Handler() {
		CamposValidator campo = new CamposValidator("tipoPessoa", "Dado inválido, escolha: JURIDICA ou FISICA");
		return campo;
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public CamposValidator Handler(EntityNotFoundException e) {
		CamposValidator campo = new CamposValidator("id", "Cliente não encontrado");
		return campo;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public CamposValidator HandlerInternalServer() {
		CamposValidator campo = new CamposValidator("CPF/CNPJ", "Cliente já cadastrado com mesmo CPF/CNPJ");
		return campo;
	}

}
