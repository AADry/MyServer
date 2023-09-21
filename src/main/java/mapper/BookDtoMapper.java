package mapper;

import dto.BookDto;
import model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookDtoMapper {
    Book toBook(BookDto bookDto);
    BookDto toDTO(Book book);
}
