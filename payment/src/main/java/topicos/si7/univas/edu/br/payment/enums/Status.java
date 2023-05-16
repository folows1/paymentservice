package topicos.si7.univas.edu.br.payment.enums;

import java.util.stream.Stream;


public enum Status {
	PENDING(1), PAID(2), CANCELED(3);

	private int code;

	private Status(int code) {
		this.code = code;
	}

	public static Status getStatus(Integer code) {
		return Stream.of(Status.values()).filter(t -> t.getCode() == code)
				.findFirst().orElseThrow(() -> new RuntimeException("Invalid unit: " +code));
	}

	public int getCode() {
		return code;

	}
}
