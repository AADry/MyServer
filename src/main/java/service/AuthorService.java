package service;

import dto.AuthorDto;
import exception.ServiceException;
import mapper.AuthorDtoMapper;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuthorRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    protected AuthorRepository authorRepository;
    private final AuthorDtoMapper mapper;

    @Autowired
    public AuthorService(AuthorDtoMapper mapper) {
        this.mapper = mapper;
    }


    public AuthorDto handleGetRequest(long id) throws ServiceException {
        AuthorDto authorDto;
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorDto = mapper.toDto(author.get());
            return authorDto;
        } else {
            throw new ServiceException("not found");
        }
    }

    public void handlePostRequest(Author author) throws IOException, ServiceException {
        authorRepository.save(author);
    }

    public void handlePutRequest(Author author) throws ServiceException {
        authorRepository.save(author);
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorRepository.delete(mapper.toAuthor(authorDto));
    }
}
