package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.BookDao;
import dto.BookDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.BookDtoMapper;
import model.Book;
import model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class BookService {

    private final BookDao bookDao;
    private final BookDtoMapper mapper;

    public BookService(BookDao bookDao, BookDtoMapper mapper) {
        this.bookDao = bookDao;
        this.mapper = mapper;
    }

    public BookDto handleGetRequest(long id) throws ServiceException {
        BookDto bookDto;
        try {
            Book book = bookDao.get(id);
            bookDto = mapper.toDTO(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return bookDto;
    }

    public void handlePostRequest(Book book) throws IOException, ServiceException {
        try {
            bookDao.save(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(Book book) throws ServiceException {
        try {
            bookDao.update(book);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        BookDto bookDto = new BookDto();
        try {
            bookDto.setId(id);
            bookDao.delete(mapper.toBook(bookDto));
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
