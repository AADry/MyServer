package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.impl.PublisherDao;
import dto.PublisherDto;
import exception.DaoException;
import exception.ServiceException;
import mapper.PublisherDtoMapper;
import model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class PublisherService {

    private final PublisherDao publisherDao;
    private final PublisherDtoMapper mapper;

    public PublisherService(PublisherDao publisherDao, PublisherDtoMapper mapper) {
        this.publisherDao = publisherDao;
        this.mapper = mapper;
    }


    public PublisherDto handleGetRequest(long id) throws ServiceException {
        PublisherDto publisherDto;
        try {
            Publisher publisher = publisherDao.get(id);
            publisherDto = mapper.toDto(publisher);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
        return publisherDto;
    }

    public void handlePostRequest(Publisher publisher) throws IOException, ServiceException {
        try {
            publisherDao.save(publisher);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handlePutRequest(Publisher publisher) throws ServiceException {
        try {
            publisherDao.update(publisher);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void handleDeleteRequest(long id) throws IOException, ServiceException {
        PublisherDto publisherDto = new PublisherDto();
        try {
            publisherDto.setId(id);
            publisherDao.delete(mapper.toPublisher(publisherDto));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
