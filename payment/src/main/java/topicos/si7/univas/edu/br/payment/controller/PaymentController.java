package topicos.si7.univas.edu.br.payment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import topicos.si7.univas.edu.br.payment.dto.PaymentDTO;
import topicos.si7.univas.edu.br.payment.dto.PaymentDTOFind;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;
import topicos.si7.univas.edu.br.payment.enums.TransactionDTO;
import topicos.si7.univas.edu.br.payment.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PaymentDTOFind> getAllPayments() {
		return service.findAll();
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void createPayment(@RequestBody @Valid PaymentDTO payment) {
		service.createPayment(payment);
	}
	
	@PutMapping("/pay/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void pay(@PathVariable Integer id, @RequestBody @Valid TransactionDTO ts) {
		service.pay(ts, id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable Integer id) {
		service.cancel(id);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PaymentEntity getPayment(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePayment(@RequestBody @Valid PaymentDTO dto, @PathVariable Integer id) {
		service.updatePayment(service.toEntity(dto), id);
	}
}
