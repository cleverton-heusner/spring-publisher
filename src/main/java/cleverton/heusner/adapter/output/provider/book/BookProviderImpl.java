package cleverton.heusner.adapter.output.provider.book;

import cleverton.heusner.adapter.output.entity.BookEntity;
import cleverton.heusner.adapter.output.mapper.BookEntityMapper;
import cleverton.heusner.adapter.output.repository.BookRepository;
import cleverton.heusner.domain.model.Book;
import cleverton.heusner.port.output.provider.book.BookProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookEntityMapper bookEntityMapper;
    private final BookRepository bookRepository;

    public BookProviderImpl(final BookEntityMapper bookEntityMapper,
                            final BookRepository bookRepository) {
        this.bookEntityMapper = bookEntityMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book register(final Book book){
        final BookEntity savedBookEntity = bookRepository.save(bookEntityMapper.toEntity(book));
        return bookEntityMapper.toModel(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(final Long id) {
        final Optional<BookEntity> bookFoundOptional = bookRepository.findById(id);
        return bookFoundOptional.map(bookEntityMapper::toModel);
    }

    @Override
    public Optional<Book> findByIsbn(final String isbn) {
        final Optional<BookEntity> bookFoundOptional = bookRepository.findByIsbn(isbn);
        return bookFoundOptional.map(bookEntityMapper::toModel);
    }

    @Override
    public Boolean existsById(final Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Boolean existsByIsbn(final String isbn){
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll().stream().map(bookEntityMapper::toModel).toList();
    }

    @Override
    public void deleteById(final Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIsbn(final String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }
}