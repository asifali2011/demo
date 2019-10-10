package com.scp.exception;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.scp.util.ApiResponse;
import com.scp.util.CommonUtil;
import com.scp.util.ErrorDetails;
import com.scp.util.Logger4j;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
	// 400

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final List<ErrorDetails> errors = new ArrayList<ErrorDetails>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setFieldName(error.getField());
			errorDetails.setMessage(error.getDefaultMessage());

			errors.add(errorDetails);
		}

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", errors);
		return handleExceptionInternal(ex, apiResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());

		final List<ErrorDetails> errors = new ArrayList<ErrorDetails>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setFieldName(error.getField());
			errorDetails.setMessage(error.getDefaultMessage());

			errors.add(errorDetails);
		}

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", errors);
		return handleExceptionInternal(ex, apiResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", error);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final String error = ex.getRequestPartName() + " part is missing";
		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", error);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final String error = ex.getParameterName() + " parameter is missing";
		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", error);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", error);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final List<ErrorDetails> errors = new ArrayList<ErrorDetails>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setFieldName(violation.getRootBeanClass().getName());
			errorDetails.setMessage(violation.getMessage());
			errors.add(errorDetails);
		}

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),
				"Server could not understand the request due to invalid syntax.", errors);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Request not found", error);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed",
				builder.toString());
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ApiResponse apiResponse = new ApiResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
				"Unsupported Media Type", builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		Logger4j.getLogger().info(ex.getClass().getName());
		Logger4j.getLogger().error("error", ex);
		final ApiResponse apiResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				CommonUtil.getRootCause(ex).getMessage(), "error occurred");
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
