package topicos.si7.univas.edu.br.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import topicos.si7.univas.edu.br.payment.dto.PaymentDTO;
import topicos.si7.univas.edu.br.payment.dto.PaymentDTOFind;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;
import topicos.si7.univas.edu.br.payment.repository.PaymentRepository;

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
	
	public void createPayment(PaymentDTO payment) {
		repo.save(toEntity(payment));
	}

	public PaymentEntity toEntity(PaymentDTO payment) {
		return new PaymentEntity(payment.getMethod(), payment.getDueDate(), payment.getPaymentValue());
	}
	
	
}
