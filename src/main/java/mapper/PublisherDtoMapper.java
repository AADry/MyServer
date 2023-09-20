package mapper;

import dto.PublisherDto;
import model.Publisher;
import org.mapstruct.Mapper;

@Mapper
public interface PublisherDtoMapper {
    PublisherDto toDto(Publisher publisher);
    Publisher toPublisher(PublisherDto publisherDto);
}
