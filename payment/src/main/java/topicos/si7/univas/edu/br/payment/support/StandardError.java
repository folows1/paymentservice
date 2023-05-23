package topicos.si7.univas.edu.br.payment.support;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

//imutável - imutable
@Getter
@AllArgsConstructor
public class StandardError {
	private String message;
	private Integer status;
	private Date date;
}
