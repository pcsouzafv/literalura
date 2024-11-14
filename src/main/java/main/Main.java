package main;

import models.Author;
import models.AuthorData;
import models.Book;
import models.BookData;
import repositories.AuthorRepository;
import repositories.BookRepository;
import services.ApiRequest;
import services.DataConverter;

import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class Main {
	private static final String MENU = """
                **********************************************
                1 - Buscar livro pelo titulo
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em determinado idioma
                6 - Top 10 livros
                7 - Buscar autores por nome
                8 - Media de downloads por autor

                0 - Sair
                **********************************************
                """;
	private static final String BASE_URL = "https://gutendex.com/books?search=";

	private Scanner scanner = new Scanner(System.in);
	private ApiRequest apiRequest = new ApiRequest();
	private AuthorRepository authorRepository;
	private BookRepository bookRepository;
	private DataConverter dataConverter = new DataConverter();

	public Main(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public void principal() {
		int option;
		do {
			printMenu();
			option = getUserInput();
			handleOption(option);
		} while (option != 0);
	}

	private void printMenu() {
		System.out.println(MENU);
	}

	private int getUserInput() {
		int option = scanner.nextInt();
		scanner.nextLine(); // consume newline left-over
		return option;
	}

	private void handleOption(int option) {
		switch (option) {
			case 1 -> buscarNovoLivro();
			case 2 -> buscarLivrosRegistrados();
			case 3 -> buscarAutoresRegistrados();
			case 4 -> buscarAutoresVivosPorAno();
			case 5 -> buscarLivrosPorIdioma();
			case 6 -> buscarTop10();
			case 7 -> buscarAutorPorNome();
			case 8 -> mediaDeDownloadsPorAutor();
			case 0 -> System.out.println("Saindo...");
			default -> System.out.println("\n\n***Opção Inválida***\n\n");
		}
	}

	private void buscarNovoLivro() {
		System.out.println("\nQual livro deseja buscar?");
		String userSearch = scanner.nextLine();
		String data = apiRequest.consumo(BASE_URL + userSearch.replace(" ", "%20"));
		salvarNoDb(data);
	}

	private void salvarNoDb(String data) {
		try {
			Book book = new Book(dataConverter.getData(data, BookData.class));
			Author author = new Author(dataConverter.getData(data, AuthorData.class));

			Author authorDb = authorRepository.existsByNome(author.getNome())
					? authorRepository.findByNome(author.getNome())
					: authorRepository.save(author);

			Book bookDb = bookRepository.existsByNome(book.getNome())
					? bookRepository.findByNome(book.getNome())
					: bookRepository.save(book);
			bookDb.setAutor(authorDb);

			System.out.println(bookDb);
		} catch (NullPointerException e) {
			System.out.println("\n\n*** Livro não encontrado ***\n\n");
		}
	}

	private void buscarLivrosRegistrados() {
		var booksDb = bookRepository.findAll();
		if (!booksDb.isEmpty()) {
			System.out.println("\nLivros cadastrados no banco de dados: ");
			booksDb.forEach(System.out::println);
		} else {
			System.out.println("\nNenhum livro encontrado no banco de dados!");
		}
	}

	private void buscarAutoresRegistrados() {
		var authorsDb = authorRepository.findAll();
		if (!authorsDb.isEmpty()) {
			System.out.println("\nAutores cadastrados no banco de dados:");
			authorsDb.forEach(System.out::println);
		} else {
			System.out.println("\nNenhum autor encontrado no banco de dados!");
		}
	}

	private void buscarAutoresVivosPorAno() {
		System.out.println("\nQual ano deseja pesquisar?");
		int yearSelected = scanner.nextInt();
		scanner.nextLine();
		var authorsDb = authorRepository.buscarPorAnoDeFalecimento(yearSelected);
		if (!authorsDb.isEmpty()) {
			System.out.println("\n\nAtores vivos no ano de: " + yearSelected);
			authorsDb.forEach(System.out::println);
		} else {
			System.out.println("\nNenhum autor encontrado para esta data!");
		}
	}

	private void buscarLivrosPorIdioma() {
		var languagesDb = bookRepository.bucasidiomas();
		System.out.println("\nIdiomas cadastrados no banco:");
		languagesDb.forEach(System.out::println);
		System.out.println("\nSelecione um dos idiomas cadastrados no banco:\n");
		String selectedLanguage = scanner.nextLine();
		bookRepository.buscarPorIdioma(selectedLanguage).forEach(System.out::println);
	}

	private void buscarTop10() {
		var top10Books = bookRepository.findTop10ByOrderByQuantidadeDeDownloadsDesc();
		top10Books.forEach(System.out::println);
	}

	private void buscarAutorPorNome() {
		System.out.println("Qual o nome do autor?");
		String authorSearch = scanner.nextLine();
		var authors = authorRepository.encontrarPorNome(authorSearch);
		if (!authors.isEmpty()) {
			authors.forEach(System.out::println);
		} else {
			System.out.println("*** Autor não encontrado! ***");
		}
	}

	private void mediaDeDownloadsPorAutor() {
		System.out.println("Qual autor deseja buscar?");
		String authorSearch = scanner.nextLine();
		var books = bookRepository.encontrarLivrosPorAutor(authorSearch);
		DoubleSummaryStatistics statistics = books.stream()
				.mapToDouble(Book::getQuantidadeDeDownloads)
				.summaryStatistics();
		System.out.println("Média de Downloads: " + statistics.getAverage());
	}
}