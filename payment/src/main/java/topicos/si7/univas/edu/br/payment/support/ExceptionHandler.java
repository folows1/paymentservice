package topicos.si7.univas.edu.br.payment.support;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<StandardError> handleObjectNotFound(ObjectNotFound ex, HttpServletRequest req) {
		
		StandardError error = new StandardError(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(PaymentException.class)
	public ResponseEntity<StandardError> handleObjectNotFound(PaymentException ex, HttpServletRequest req) {
		
		StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationError objectNotFound(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError error = new ValidationError(
			HttpStatus.BAD_REQUEST.value(), "Validation error.", new Date());
		e.getBindingResult().getFieldErrors().stream()
			.forEach(err -> error.addError(err.getField(), err.getDefaultMessage()));
		return error;
	}
}
