package service;

import com.google.gson.Gson;
import dao.impl.AuthorDao;
import dto.AuthorDto;
import dto.BookDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.AuthorDtoMapper;
import mapper.BookDtoMapper;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthorService {
    private final AuthorDao authorDao;
    private final AuthorDtoMapper mapper;

    public AuthorService(AuthorDao authorDao, AuthorDtoMapper mapper) {
        this.authorDao = authorDao;
        this.mapper = mapper;
    }


    public AuthorDto handleGetRequest(long id) throws ServiceException {
        AuthorDto authorDto;
        try {
            Author author = authorDao.get(id);
            authorDto = mapper.toDto(author);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return authorDto;
    }

    public void handlePostRequest(Author author) throws IOException, ServiceException {
        try {
            authorDao.update(author);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(Author author) throws ServiceException {
        try {
            authorDao.update(author);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        try {
            authorDao.delete(mapper.toAuthor(authorDto));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
