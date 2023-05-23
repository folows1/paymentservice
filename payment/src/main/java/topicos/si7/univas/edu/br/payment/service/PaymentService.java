package topicos.si7.univas.edu.br.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import topicos.si7.univas.edu.br.payment.dto.PaymentDTO;
import topicos.si7.univas.edu.br.payment.dto.PaymentDTOFind;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;
import topicos.si7.univas.edu.br.payment.enums.Status;
import topicos.si7.univas.edu.br.payment.enums.TransactionDTO;
import topicos.si7.univas.edu.br.payment.repository.PaymentRepository;
import topicos.si7.univas.edu.br.payment.support.ObjectNotFound;

@Service
public class PaymentService {
	
	private PaymentRepository repo;

	@Autowired
	public PaymentService(PaymentRepository repo) {
		this.repo = repo;
	}
	
	public List<PaymentDTOFind> findAll() {
		return repo.findAll().stream().map(p -> new PaymentDTOFind(p)).collect(Collectors.toList());
	}
	
	public PaymentEntity findById(int id) {
		Optional<PaymentEntity> obj = repo.findById(id);
		PaymentEntity payment = obj.orElseThrow(() -> new ObjectNotFound("Payment not found - " +id));
		
		return payment;
	}
	
	public void createPayment(PaymentDTO payment) {
		repo.save(toEntity(payment));
	}

	public PaymentEntity toEntity(PaymentDTO payment) {
		return new PaymentEntity(payment.getMethod(), payment.getDueDate(), payment.getPaymentValue());
	}
	
	public void pay (TransactionDTO ts, int id) {
		Optional<PaymentEntity> obj = repo.findById(id);
		PaymentEntity payment = obj.orElseThrow(() -> new ObjectNotFound("Payment not found - " +id));
		payment.setTransactionId(ts.getTransactionId());
		payment.setPaidAt(LocalDateTime.now());
		payment.setStatus(Status.PAID);
		repo.save(payment);
	}
	
	public void cancel (int id) {
		Optional<PaymentEntity> obj = repo.findById(id);
		PaymentEntity payment = obj.orElseThrow(() -> new ObjectNotFound("Payment not found - " +id));
		payment.setStatus(Status.CANCELED);
		payment.setPaidAt(null);
		payment.setTransactionId(0);
		repo.save(payment);
	}
	
	public void updatePayment (PaymentEntity payment, Integer id) {
		if (id == null || payment == null) {
			throw new RuntimeException("Invalid id.");
		}
		
		PaymentEntity ent = findById(id);
		updateData(ent, payment);
		repo.save(ent);
	}
	
	private void updateData(PaymentEntity ent, PaymentEntity payment) {
		ent.setMethod(payment.getMethod());
		ent.setDueDate(payment.getDueDate());
		ent.setPaymentValue(payment.getPaymentValue());
	}
	
}
