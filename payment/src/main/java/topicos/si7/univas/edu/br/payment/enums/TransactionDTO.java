package topicos.si7.univas.edu.br.payment.enums;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDTO {
	
	@NotNull(message = "TransactionID can not be null.")
	@Positive(message = "TransactionID must not be negative.")
	private int transactionId;
}
