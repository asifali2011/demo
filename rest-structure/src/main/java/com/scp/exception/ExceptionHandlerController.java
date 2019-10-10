package com.scp.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.scp.util.ApiResponse;

@ControllerAdvice
public class ExceptionHandlerController implements ErrorController {

	@RequestMapping("/error")
	@ResponseBody
	public ResponseEntity<Object> handleError(HttpServletRequest request) {
		int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

		final ApiResponse apiResponse = new ApiResponse(statusCode, HttpStatus.valueOf(statusCode).toString(),
				exception);
		return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.valueOf(statusCode));
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
