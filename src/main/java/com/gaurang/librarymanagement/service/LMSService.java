package com.gaurang.librarymanagement.service;

import com.gaurang.librarymanagement.entity.Book;
import com.gaurang.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LMSService {

    @Autowired
    private BookRepository bookRepository;

    /**
     *
     * @param sortField
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Book> getAllCatalogue(Optional<String> sortField,
                                      Optional<Integer> offset,
                                      Optional<Integer> pageSize) throws Exception {
        if (sortField.isPresent() && offset.isPresent() && pageSize.isPresent()) {
            return bookRepository.findAll(PageRequest.of(offset.get(), pageSize.get())
                    .withSort(Sort.by(sortField.get()))).stream().toList();
        } else if (offset.isPresent() && pageSize.isPresent()) {
            return bookRepository.findAll(PageRequest.of(offset.get(), pageSize.get())).stream().toList();
        } else if (sortField.isPresent()) {
            return bookRepository.findAll(Sort.by(sortField.get()));
        }
        return bookRepository.findAll();

    }

    /**
     *
     * @param bookId
     * @param bookName
     * @param bookAuthor
     * @param bookCatalogue
     * @param bookPublisher
     * @return
     * @throws Exception
     */
    public List<Book> getBookByField(Optional<Integer> bookId, Optional<String> bookName,
                                     Optional<String> bookAuthor, Optional<Integer> bookCatalogue,
                                     Optional<String> bookPublisher) throws Exception{
        if (bookId.isPresent()) {
            return bookRepository.findById(bookId.get()).stream().toList();
        } else if (bookName.isPresent()) {
            return bookRepository.findAllByBookName(bookName.get());
        } else if (bookAuthor.isPresent()) {
            return bookRepository.findAllByBookAuthor(bookAuthor.get());
        } else if (bookCatalogue.isPresent()) {
            return bookRepository.findAllByBookCatalogue(bookCatalogue.get());
        } else if (bookPublisher.isPresent()) {
            return bookRepository.findAllByBookPublisher(bookPublisher.get());
        }
        return null;
    }

    /**
     *
     * @param book
     * @return
     * @throws Exception
     */
    public Book addBook(Book book) throws Exception {
        return bookRepository.save(book);
    }

    /**
     *
     * @param bookId
     * @param bookName
     * @param bookAuthor
     * @param bookCatalogue
     * @param bookPublisher
     * @return
     * @throws Exception
     */
    @Transactional
    public String deleteBook(Optional<Integer> bookId, Optional<String> bookName, Optional<String> bookAuthor,
                      Optional<Integer> bookCatalogue, Optional<String> bookPublisher) throws Exception{
        if (bookId.isPresent() && bookRepository.findById(bookId.get()).isPresent()) {
            if (bookName.isPresent() && bookAuthor.isPresent() &&
                    bookCatalogue.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogueAndBookPublisher(
                        bookId.get(), bookName.get(), bookAuthor.get(), bookCatalogue.get(), bookPublisher.get());
            } else if (bookName.isPresent() && bookAuthor.isPresent() && bookCatalogue.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogue(bookId.get(),
                        bookName.get(), bookAuthor.get(), bookCatalogue.get());
            } else if (bookName.isPresent() && bookAuthor.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookAuthorAndBookPublisher(bookId.get(),
                        bookName.get(), bookAuthor.get(), bookPublisher.get());
            } else if (bookName.isPresent() && bookCatalogue.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookCatalogueAndBookPublisher(bookId.get(),
                        bookName.get(), bookCatalogue.get(), bookPublisher.get());
            } else if (bookAuthor.isPresent() && bookCatalogue.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookAuthorAndBookCatalogueAndBookPublisher(bookId.get(),
                        bookAuthor.get(), bookCatalogue.get(), bookPublisher.get());
            } else if (bookName.isPresent() && bookAuthor.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookAuthor(bookId.get(), bookName.get(),
                        bookAuthor.get());
            } else if (bookAuthor.isPresent() && bookCatalogue.isPresent()) {
                bookRepository.deleteByBookIdAndBookAuthorAndBookCatalogue(bookId.get(), bookAuthor.get(),
                        bookCatalogue.get());
            } else if (bookName.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookPublisher(bookId.get(), bookName.get(),
                        bookPublisher.get());
            } else if (bookAuthor.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookAuthorAndBookPublisher(bookId.get(), bookAuthor.get(),
                        bookPublisher.get());
            } else if (bookName.isPresent() && bookCatalogue.isPresent()) {
                bookRepository.deleteByBookIdAndBookNameAndBookCatalogue(bookId.get(), bookName.get(),
                        bookCatalogue.get());
            } else if (bookCatalogue.isPresent() && bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookCatalogueAndBookPublisher(bookId.get(), bookCatalogue.get(),
                        bookPublisher.get());
            } else if (bookName.isPresent()) {
                bookRepository.deleteByBookIdAndBookName(bookId.get(), bookName.get());
            } else if (bookAuthor.isPresent()) {
                bookRepository.deleteByBookIdAndBookAuthor(bookId.get(), bookAuthor.get());
            } else if (bookCatalogue.isPresent()) {
                bookRepository.deleteByBookIdAndBookCatalogue(bookId.get(), bookCatalogue.get());
            } else if (bookPublisher.isPresent()) {
                bookRepository.deleteByBookIdAndBookPublisher(bookId.get(), bookPublisher.get());
            }
            bookRepository.deleteByBookId(bookId.get());
            return "Entry has been deleted!";
        }
        return null;
    }

}
