package zupbootcamp.com.br.bankapi.domain.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sobrenome;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String cnh;

	@Column(nullable = false)
	private String cpf;
	
	@OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

	@Column(nullable = false)
	private LocalDate dataNascimento;

	public String getdataNascimento() {
		return dataNascimento.toString();
	}

	public void setdataNascimento(String dataNascimento) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dataNascimento, formatter);

		this.dataNascimento = data;
	}

}
