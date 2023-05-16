package topicos.si7.univas.edu.br.payment.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import topicos.si7.univas.edu.br.payment.enums.Status;

@Entity
@Data
@NoArgsConstructor
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String method;
	private Date dueDate;
	private float paymentValue;
	private LocalDateTime paidAt;
	private int transactionId;
	private Status status;
	
	public PaymentEntity(String method, Date dueDate, float paymentValue) {
		super();
		this.method = method;
		this.dueDate = dueDate;	
		this.paymentValue = paymentValue;
		this.status = Status.PENDING;
	}
}
