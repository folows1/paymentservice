package topicos.si7.univas.edu.br.payment.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String method;
	private String dueDate;
	private float paymentValue;
	private String paidAt;
	private int transactionId;
	
	public PaymentEntity(String method, String dueDate, float paymentValue) {
		super();
		this.method = method;
		this.dueDate = dueDate;	
		this.paymentValue = paymentValue;
	}
}
