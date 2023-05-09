package topicos.si7.univas.edu.br.payment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;

@Data
@NoArgsConstructor
public class PaymentDTO {
	
	@NotNull(message = "Id can not be null.")
	@Positive(message = "Id must not be negative.")
	private Integer id;
	
	@NotNull(message = "Method can not be null.")
	@NotEmpty(message = "Method can not be empty.")
	@Size(min = 3, max = 80, message = "The size must be between 3 and 80.")
	private String method;
	
	private String dueDate;
	
	@NotNull(message = "Value can not be null.")
	@Positive(message = "Value must not be negative.")
	private float paymentValue;

	
	public PaymentDTO(PaymentEntity payment) {
		this.id = payment.getId();
		this.method = payment.getMethod();
		this.paymentValue = payment.getPaymentValue();
		this.dueDate = payment.getDueDate();
	}
}
