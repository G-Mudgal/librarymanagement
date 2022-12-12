package com.gaurang.librarymanagement.repository;

import com.gaurang.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByBookName(String bookName);

    List<Book> findAllByBookAuthor(String bookAuthor);

    List<Book> findAllByBookCatalogue(Integer bookCatalogue);

    List<Book> findAllByBookPublisher(String bookPublisher);

    void deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogueAndBookPublisher(Integer bookId, String bookName,
                                                                                String bookAuthor,
                                                                                Integer bookCatalogue,
                                                                                String bookPublisher);

    void deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogue(Integer bookId, String bookName, String bookAuthor,
                                                                Integer bookCatalogue);

    void deleteByBookIdAndBookAuthorAndBookCatalogueAndBookPublisher(Integer bookId, String bookAuthor,
                                                                     Integer bookCatalogue, String bookPublisher);

    void deleteByBookIdAndBookNameAndBookCatalogueAndBookPublisher(Integer bookId, String bookName,
                                                                   Integer bookCatalogue, String bookPublisher);

    void deleteByBookIdAndBookNameAndBookAuthorAndBookPublisher(Integer bookId, String bookName,
                                                                String bookAuthor, String bookPublisher);

    void deleteByBookIdAndBookNameAndBookAuthor(Integer bookId, String bookName, String bookAuthor);

    void deleteByBookIdAndBookAuthorAndBookCatalogue(Integer bookId, String bookAuthor, Integer bookCatalogue);

    void deleteByBookIdAndBookNameAndBookPublisher(Integer bookId, String bookName, String bookPublisher);

    void deleteByBookIdAndBookAuthorAndBookPublisher(Integer bookId, String bookAuthor, String bookPublisher);

    void deleteByBookIdAndBookNameAndBookCatalogue(Integer bookId, String bookName, Integer bookCatalogue);

    void deleteByBookIdAndBookCatalogueAndBookPublisher(Integer bookId, Integer bookCatalogue, String bookPublisher);

    void deleteByBookIdAndBookName(Integer bookId, String bookName);

    void deleteByBookIdAndBookAuthor(Integer bookId, String bookAuthor);

    void deleteByBookIdAndBookCatalogue(Integer bookId, Integer bookCatalogue);

    void deleteByBookIdAndBookPublisher(Integer bookId, String bookPublisher);

    void deleteByBookId(Integer bookId);

}
