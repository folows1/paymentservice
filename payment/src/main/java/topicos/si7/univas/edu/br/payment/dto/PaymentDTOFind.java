package topicos.si7.univas.edu.br.payment.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;
import topicos.si7.univas.edu.br.payment.enums.Status;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTOFind {
	private Integer id;
	private String method;
	private Date dueDate;
	private float paymentValue;
	private LocalDateTime paidAt;
	private Integer transactionId;
	private Status status;
	
	public PaymentDTOFind(PaymentEntity p) {
		super();
		this.id = p.getId();
		this.method = p.getMethod();
		this.dueDate = p.getDueDate();
		this.paymentValue = p.getPaymentValue();
		this.paidAt = p.getPaidAt();
		this.transactionId = p.getTransactionId();
		this.status = p.getStatus();
	}
}
