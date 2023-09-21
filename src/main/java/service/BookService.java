package service;

import dto.BookDto;
import exception.ServiceException;
import mapper.BookDtoMapper;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    protected BookRepository bookRepository;
    private final BookDtoMapper mapper;

    @Autowired
    public BookService(BookDtoMapper mapper) {
        this.mapper = mapper;
    }

    public BookDto handleGetRequest(long id) throws ServiceException {
        BookDto bookDto;
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookDto = mapper.toDTO(book.get());
            return bookDto;
        } else {
            throw new ServiceException("not found");
        }
    }

    public void handlePostRequest(Book book) throws IOException, ServiceException {
        bookRepository.save(book);
    }

    public void handlePutRequest(Book book) throws ServiceException {
        bookRepository.save(book);
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookRepository.delete(mapper.toBook(bookDto));
    }
}
