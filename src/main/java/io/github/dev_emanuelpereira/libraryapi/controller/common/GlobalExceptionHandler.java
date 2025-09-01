package io.github.dev_emanuelpereira.libraryapi.controller.common;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroCampo;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
import io.github.dev_emanuelpereira.libraryapi.exceptions.CampoInvalidoException;
import io.github.dev_emanuelpereira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleGenericException(RuntimeException e){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado. Tente novamente mais tarde!", List.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAcessDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Você não possui permissão para este módulo do sistema.", List.of());
    }
}
