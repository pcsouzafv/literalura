package models;

import jakarta.persistence.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nome;
	private int dataDeNascimento;
	private int dataDeFalecimento;
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Book> livros = new ArrayList<>();

	public Author(){}
	public Author(AuthorData data) {
		this.nome = data.nomeDoAutor();
		this.dataDeNascimento = data.anoDeNascimento();
		this.dataDeFalecimento = data.anoDeFalecimento();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(int dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public int getDataDeFalecimento() {
		return dataDeFalecimento;
	}

	public void setDataDeFalecimento(int dataDeFalecimento) {
		this.dataDeFalecimento = dataDeFalecimento;
	}

	public List<Book> getLivros() {
		return livros;
	}

	public void setLivros(List<Book> livros) {
		livros.forEach(l -> l.setAutor(this));
		this.livros = livros;
	}


	@Override
	public String toString() {
		return "---------------------------------------"+
				"\nNome: " + nome +
				"\nData De Nascimento: " + dataDeNascimento +
				"\nData De Falecimento: " + dataDeFalecimento+
				"\n---------------------------------------";
	}
}

