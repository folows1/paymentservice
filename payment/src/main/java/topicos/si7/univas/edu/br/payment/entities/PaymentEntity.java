package topicos.si7.univas.edu.br.payment.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

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
	private float value;
	private String paidAt;
	private int transactionId;
	
	public PaymentEntity(String method, String dueDate, float value) {
		super();
		this.method = method;
		this.dueDate = dueDate;	
		this.value = value;
	}
}
