package models;

import jakarta.persistence.*;

@Entity
@Table(name= "livros")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nome;
	private String idioma;
	private Integer quantidadeDeDownloads;
	@ManyToOne
	private Author autor;

	public Book(){}
	public Book(BookData data) {
		this.nome = data.nomeDoLivro();
		this.idioma = String.join(",",data.idiomas());
		this.quantidadeDeDownloads = data.quantidadeDeDownloads();
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

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Integer getQuantidadeDeDownloads() {
		return quantidadeDeDownloads;
	}

	public void setQuantidadeDeDownloads(Integer quantidadeDeDownloads) {
		this.quantidadeDeDownloads = quantidadeDeDownloads;
	}

	public Author getAutor() {
		return autor;
	}

	public void setAutor(Author autor) {
		this.autor = autor;
	}


	@Override
	public String toString() {
		return "\n---------------------------------------"+
				"\nNome: " + nome +
				"\nIdioma: " + idioma +
				"\nAutor: " + autor.getNome() +
				"\nQuantidade De Downloads: " + quantidadeDeDownloads;
	}
}
