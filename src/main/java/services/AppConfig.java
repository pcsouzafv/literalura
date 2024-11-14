package services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import repositories.AuthorRepository;
import repositories.BookRepository;

@Configuration
public class AppConfig {
    
    @Bean
    public ApiRequest apiRequest() {
        return new ApiRequest();
    }
    
    @Bean
    public DataConverter dataConverter() {
        return new DataConverter();
    }
    
    @Bean
    public BookService bookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            ApiRequest apiRequest,
            DataConverter dataConverter, Object newParam) {
        return new BookService();
    }
}

