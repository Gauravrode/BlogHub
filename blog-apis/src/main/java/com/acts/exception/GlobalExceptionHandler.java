package com.acts.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.acts.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handlerMethodNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> response=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			String fieldName=((FieldError) error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName,message);
		});	
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Map<String, String>> handleApiException(ApiException ex) {
	    Map<String, String> errorResponse = new HashMap<>();
	    System.out.println("✅ GlobalExceptionHandler Caught ApiException: " + ex.getMessage());
	    errorResponse.put("message", ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
}

