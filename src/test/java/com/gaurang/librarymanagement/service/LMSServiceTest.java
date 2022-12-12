package com.gaurang.librarymanagement.service;

import com.gaurang.librarymanagement.entity.Book;
import com.gaurang.librarymanagement.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LMSServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LMSService lmsService;

    Book book1 = new Book(1, "ABC", "ABC", 1,
            17F, "ABC");

    Book book2 = new Book(2, "DEF", "DEF", 1,
            20F, "DEF");

    Book book3 = new Book(3, "GHI", "ABC", 2,
            15F, "ABC");

    @Before
    public void setup() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(lmsService).build();
    }

    @Test
    public void getAllCatalogueWithSortOnlyTest() throws Exception {
        Mockito.when(bookRepository.findAll((Sort) any())).thenReturn(List.of(book3, book1, book2));
        List<Book> retBooks = lmsService.getAllCatalogue(Optional.of("bookPrice"), Optional.empty(),
                Optional.empty());
        assertEquals("GHI", retBooks.get(0).getBookName());
    }

    @Test
    public void getAllCatalogueWithPaginationOnlyTest() throws Exception {
        Page<Book> retPage = new PageImpl<>(List.of(book1, book2));
        Mockito.when(bookRepository.findAll((PageRequest) any())).thenReturn(retPage);
        List<Book> retBooks = lmsService.getAllCatalogue(Optional.empty(), Optional.of(0),
                Optional.of(2));
        assertEquals(2, retBooks.size());
    }

    @Test
    public void getAllCatalogueWithPaginationAndSortOnlyTest() throws Exception {
        Page<Book> retPage = new PageImpl<>(List.of(book3, book1));
        Mockito.when(bookRepository.findAll((PageRequest) any())).thenReturn(retPage);
        List<Book> retBooks = lmsService.getAllCatalogue(Optional.of("bookPrice"), Optional.of(0), Optional.of(2));
        assertEquals(2, retBooks.size());
        assertEquals("GHI", retBooks.get(0).getBookName());
    }

    @Test
    public void getAllCatalogueWithNoPaginationAndSortTest() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1, book2, book3));
        List<Book> retBooks = lmsService.getAllCatalogue(Optional.empty(), Optional.empty(),
                Optional.empty());
        assertEquals(3, retBooks.size());
    }

    @Test
    public void addBookTest() throws Exception {
        Mockito.when(bookRepository.save(any())).thenReturn(book1);
        Book retBook = lmsService.addBook(any());
        assertEquals("ABC", retBook.getBookName());
    }

    @Test
    public void getBookByBookIdTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        List<Book> retBooks = lmsService.getBookByField(Optional.of(1), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals("ABC", retBooks.get(0).getBookName());
    }

    @Test
    public void getBookByBookNameTest() throws Exception {
        Mockito.when(bookRepository.findAllByBookName(anyString())).thenReturn(List.of(book1));
        List<Book> retBooks = lmsService.getBookByField(Optional.empty(), Optional.of("ABC"),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals("ABC", retBooks.get(0).getBookName());
    }

    @Test
    public void getBookByBookAuthorTest() throws Exception {
        Mockito.when(bookRepository.findAllByBookAuthor(anyString())).thenReturn(List.of(book1));
        List<Book> retBooks = lmsService.getBookByField(Optional.empty(), Optional.empty(),
                Optional.of("ABC"), Optional.empty(), Optional.empty());
        assertEquals("ABC", retBooks.get(0).getBookName());
    }

    @Test
    public void getBookByBookCatalogueTest() throws Exception {
        Mockito.when(bookRepository.findAllByBookCatalogue(anyInt())).thenReturn(List.of(book1));
        List<Book> retBooks = lmsService.getBookByField(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(1), Optional.empty());
        assertEquals("ABC", retBooks.get(0).getBookName());
    }

    @Test
    public void getBookByBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findAllByBookPublisher(anyString())).thenReturn(List.of(book1));
        List<Book> retBooks = lmsService.getBookByField(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of("ABC"));
        assertEquals("ABC", retBooks.get(0).getBookName());
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogueAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogueAndBookPublisher(anyInt(), anyString(),
                        anyString(), anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.of("ABC"), Optional.of(1), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogueTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookAuthorAndBookCatalogue(anyInt(), anyString(),
                        anyString(), anyInt());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.of("ABC"), Optional.of(1), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookAuthorAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookAuthorAndBookPublisher(anyInt(), anyString(),
                        anyString(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.of("ABC"), Optional.empty(), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookCatalogueAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookCatalogueAndBookPublisher(anyInt(), anyString(),
                        anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.empty(), Optional.of(1), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookAuthorAndBookCatalogueAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookAuthorAndBookCatalogueAndBookPublisher(anyInt(), anyString(),
                        anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.of("ABC"), Optional.of(1), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookAuthorTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookAuthor(anyInt(), anyString(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.of("ABC"), Optional.empty(), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookAuthorAndBookCatalogueTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookAuthorAndBookCatalogue(anyInt(), anyString(), anyInt());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.of("ABC"), Optional.of(1), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookPublisher(anyInt(), anyString(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.empty(), Optional.empty(), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookAuthorAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookAuthorAndBookPublisher(anyInt(), anyString(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.of("ABC"), Optional.empty(), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameAndBookCatalogueTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookNameAndBookCatalogue(anyInt(), anyString(), anyInt());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.empty(), Optional.of(1), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookCatalogueAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookCatalogueAndBookPublisher(anyInt(), anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.empty(), Optional.of(1), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookNameTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookName(anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.of("ABC"),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookAuthorTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookAuthor(anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.of("ABC"), Optional.empty(), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookCatalogueTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookCatalogue(anyInt(), anyInt());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.empty(), Optional.of(1), Optional.empty());
        assertEquals("Entry has been deleted!", retString);
    }

    @Test
    public void deleteByBookIdAndBookPublisherTest() throws Exception {
        Mockito.when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book1));
        Mockito.doNothing().when(bookRepository)
                .deleteByBookIdAndBookPublisher(anyInt(), anyString());
        String retString = lmsService.deleteBook(Optional.of(1), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of("ABC"));
        assertEquals("Entry has been deleted!", retString);
    }

}
