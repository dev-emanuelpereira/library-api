package io.github.dev_emanuelpereira.libraryapi.controller.common;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroCampo;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro validação", erroCampos);
    }
}
