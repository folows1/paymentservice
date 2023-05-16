package topicos.si7.univas.edu.br.payment.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;

@Data
@NoArgsConstructor
public class PaymentDTO {
	
	@NotNull(message = "Method can not be null.")
	@NotEmpty(message = "Method can not be empty.")
	@Size(min = 3, max = 80, message = "The size must be between 3 and 80.")
	private String method;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
	private Date dueDate;
	
	@NotNull(message = "Value can not be null.")
	@Positive(message = "Value must not be negative.")
	private float paymentValue;

	
	public PaymentDTO(PaymentEntity payment) {
		this.method = payment.getMethod();
		this.paymentValue = payment.getPaymentValue();
		this.dueDate = payment.getDueDate();
	}
}
