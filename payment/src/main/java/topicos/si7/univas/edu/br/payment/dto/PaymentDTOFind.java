package topicos.si7.univas.edu.br.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTOFind {
	private Integer id;
	private String method;
	private String dueDate;
	private float paymentValue;
	private String paidAt;
	private Integer transactionId;
	
	public PaymentDTOFind(PaymentEntity p) {
		super();
		this.method = p.getMethod();
		this.dueDate = p.getDueDate();
		this.paymentValue = p.getPaymentValue();
		this.paidAt = p.getPaidAt();
		this.transactionId = p.getTransactionId();
	}
}
