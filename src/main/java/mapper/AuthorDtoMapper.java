package mapper;

import dto.AuthorDto;
import model.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorDtoMapper {
    Author toAuthor(AuthorDto authorDto);
    AuthorDto toDto(Author author);
}
