package topicos.si7.univas.edu.br.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import topicos.si7.univas.edu.br.payment.repository.PaymentRepository;

@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {
	
	@Autowired
	private PaymentRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	}

}
