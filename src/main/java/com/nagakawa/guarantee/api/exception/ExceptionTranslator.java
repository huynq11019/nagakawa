package com.nagakawa.guarantee.api.exception;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;

import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.api.util.HeaderUtil;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The error response
 * follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@RestControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return entity;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
                .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ApiConstants.ErrorType.DEFAULT_TYPE
                        : problem.getType())
                .withStatus(problem.getStatus()).withTitle(problem.getTitle()).with(ApiConstants.ErrorKey.PATH,
                        request.getNativeRequest(HttpServletRequest.class).getRequestURI());

        if (problem instanceof ConstraintViolationProblem) {
            builder.with(ApiConstants.ErrorKey.VIOLATIONS, ((ConstraintViolationProblem) problem).getViolations())
                    .with(ApiConstants.ErrorKey.MESSAGE, LabelKey.ERROR_CONSTRAINT_VIOLATION);
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(ApiConstants.ErrorKey.MESSAGE)
                    && problem.getStatus() != null) {
                builder.with(ApiConstants.ErrorKey.MESSAGE, "error.http." + problem.getStatus().getStatusCode());
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldError(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), f.getCode()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder().withType(ApiConstants.ErrorType.CONSTRAINT_VIOLATION_TYPE)
                .withTitle(Labels.getLabels(LabelKey.ERROR_METHOD_ARGUMENT_NOT_VALID))
                .withStatus(defaultConstraintViolationStatus())
                .with(ApiConstants.ErrorKey.MESSAGE, LabelKey.ERROR_METHOD_ARGUMENT_NOT_VALID)
                .with(ApiConstants.ErrorKey.FIELD_ERRORS, fieldErrors).build();

        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex,
            NativeWebRequest request) {
        return create(ex, request,
                HeaderUtil.createFailureAlert(true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.CONFLICT)
                .with(ApiConstants.ErrorKey.MESSAGE, LabelKey.ERROR_CONCURRENCY_FAILURE).build();

        return create(ex, problem, request);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class FieldError implements Serializable {
        private static final long serialVersionUID = -3173266024911499987L;

        private final String objectName;

        private final String field;

        private final String message;
    }
}
