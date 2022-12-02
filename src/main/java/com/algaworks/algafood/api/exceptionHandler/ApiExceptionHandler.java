package com.algaworks.algafood.api.exceptionHandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler<handleExceptionInternal> extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno no sistema. Teste novamente ou contacte o adm do sistema";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleErroSistema(Exception exception, WebRequest request) {
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
        Problem problema = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ProblemType.ERRO_SISTEMA, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        exception.printStackTrace();

        return handleExceptionInternal(exception, problema, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    // metodo para tratar exceptions que acontecem dentro do ontrollador
    // vamos tratar e retornar um ResponseEntity
    // O exceptionHandler tambem verifica a causa da exception.. se a causa foi passada (Qual a exception causou).. se foi EntidadeNaoEncontradaException
    // ele entra aqui
    @ExceptionHandler(EntidadeNaoEncontradaException.class)  // captura as exceptions dentro do controllador
    public ResponseEntity<?> handleEntidadeNaoEncontrado(EntidadeNaoEncontradaException ex, WebRequest request) {

        Problem problema = createProblemBuilder (HttpStatus.NOT_FOUND, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // WebRequest  : a gente nao tem uma instancia de WebRequest
    // mas a gente pode receber como parametro do metodo: o Spring ja passa um WebRequest automaticamente (que representa uma requisicao WEB)
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        Problem problema = createProblemBuilder (HttpStatus.BAD_REQUEST, ProblemType.ERRO_DE_NEGOCIO, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

        Problem problema = createProblemBuilder (HttpStatus.CONFLICT, ProblemType.ENTIDADE_EM_USO, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), status, request);
    }


    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult  bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {

          String detail = "Um dos campos estah invalido. Preencha corretamente";

          List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                    .map( objectError -> {
                        String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                        String name = objectError.getObjectName();

                        if (objectError instanceof FieldError) {
                            name = ((FieldError) objectError).getField();
                        }

                        return Problem.Object.builder()
                                .name(name)
                                .userMessage(message).build() ;  //fieldError.getDefaultMessage()
                    })
                    .collect(Collectors.toList());

         Problem problema = createProblemBuilder(status, ProblemType.DADO_INVALIDO, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        System.out.println ("Erro GERAL handleTypeMismatch");

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("O parametro '%s' da URL '%s' recebeu  o valor '%s', que eh um valor invalido. Corrija para o tipo '%s'",
          ex.getName(), ((ServletWebRequest)request).getRequest().getRequestURL().toString() , ex.getValue(), ex.getRequiredType().getSimpleName());

//        String detail = String.format("O parametro de URL '%s' recebeu  o valor '%s', que eh um valor invalido. Corrija para o tipo '%s'",
//                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problema = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    // outra implementacao da classe pai para ter uma mensagem mais completa
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // Throwable classe java para ter pilha de exceptions.. e pego a causa
       // Throwable cause = ex.getCause();
        Throwable cause = ExceptionUtils.getRootCause(ex);
        System.out.println("causa=" + ex.getCause());

        // se causa da exception foi InvalidFormatException.. vamos tratar esa message de forma especifica
        if (cause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) cause, headers, status, request);
        } else if (cause instanceof IgnoredPropertyException) {
            return handleIgnoredProperty((IgnoredPropertyException) cause, headers, status, request);
        } else if (cause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedProperty((UnrecognizedPropertyException) cause, headers, status, request);
        }

        String detail = "Corpo da Requisicao invalido. Verifique sintaxe!";
        Problem problema = createProblemBuilder (status, ProblemType.MSG_IMCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format(" O Recurso %s, que voce tentou acessar, eh inexistente", ex.getRequestURL());

        Problem problema = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal (ex, problema,  headers, status, request);
    }

    private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' nao eh permitida. Ela nao existe no mapeamento", path);

        Problem problema = createProblemBuilder(status, ProblemType.MSG_IMCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }


    private ResponseEntity<Object> handleIgnoredProperty(IgnoredPropertyException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' nao eh permitida. Ela esta marcada com IGNORE no mapeamento", path);

        Problem problema = createProblemBuilder(status, ProblemType.MSG_IMCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }


    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       // Stream<JsonMappingException.Reference> path = ex.getPath().stream();
        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' recebeu o valor '%s' , que eh um valor invalido. Corrija para o tipo '%s'",
                path, ex.getValue(),
                ex.getTargetType().getSimpleName());

        Problem problema = createProblemBuilder (status, ProblemType.MSG_IMCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }


    // customizamos aqui todas as respostas de todos os exceptions handlers em ResponseEntityExceptionHandler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL + "teste")
                    .timestamp(OffsetDateTime.now()).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title(body.toString())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(OffsetDateTime.now()).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    private Problem.ProblemBuilder createProblemBuilder (HttpStatus status,
                                                         ProblemType problemType,
                                                         String detail) {

        return Problem.builder()
                .status(status.value())
                .type(problemType.getUrl())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }


    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

}
