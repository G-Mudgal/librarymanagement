package com.gaurang.librarymanagement.controller;

import com.gaurang.librarymanagement.config.CustomUserDetailsService;
import com.gaurang.librarymanagement.config.TokenHelper;
import com.gaurang.librarymanagement.entity.Book;
import com.gaurang.librarymanagement.entity.LoginRecord;
import com.gaurang.librarymanagement.service.LMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LMSController {

    private final LMSService lmsService;

    private final CustomUserDetailsService customUserDetailsService;

    private final TokenHelper tokenHelper;

    private final AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(LMSController.class);

    @Autowired
    public LMSController(LMSService lmsService, CustomUserDetailsService customUserDetailsService,
                         TokenHelper tokenHelper, AuthenticationManager authenticationManager) {
        this.lmsService = lmsService;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenHelper = tokenHelper;
        this.authenticationManager = authenticationManager;
    }

    /**
     *
     * @return
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to Library Management System!";
    }

    /**
     *
     * @param loginRecord
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRecord loginRecord) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRecord.userName(), loginRecord.password()));
        } catch (Exception e) {
            return "Some exception occurred!";
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRecord.userName());
        return tokenHelper.generateToken(userDetails);
    }

    /**
     *
     * @param sortField
     * @param offset
     * @param pageSize
     * @return
     */
    @GetMapping("/library/catalogue")
    public List<Book> getAllCatalogue(@RequestParam Optional<String> sortField,
                                      @RequestParam Optional<Integer> offset,
                                      @RequestParam Optional<Integer> pageSize) {
        try {
            return lmsService.getAllCatalogue(sortField, offset, pageSize);
        } catch (Exception e) {
            logger.error("Some exception occurred in getAllCatalogue", e);
            return null;
        }
    }

    /**
     *
     * @param bookId
     * @param bookName
     * @param bookAuthor
     * @param bookCatalogue
     * @param bookPublisher
     * @return
     */
    @GetMapping("/library")
    public List<Book> getBookByField(@RequestParam Optional<Integer> bookId,
                                     @RequestParam Optional<String> bookName,
                                     @RequestParam Optional<String> bookAuthor,
                                     @RequestParam Optional<Integer> bookCatalogue,
                                     @RequestParam Optional<String> bookPublisher) {
        try {
            return lmsService.getBookByField(bookId, bookName, bookAuthor, bookCatalogue, bookPublisher);
        } catch (Exception e) {
            logger.error("Some exception occurred in getBookByField", e);
            return null;
        }
    }

    /**
     *
     * @param book
     * @return
     */
    @PostMapping("/library")
    public Book addBook(@RequestBody Book book) {
        try {
            return lmsService.addBook(book);
        } catch (Exception e) {
            logger.error("Some exception occurred in addBook", e);
            return null;
        }
    }

    /**
     *
     * @param bookId
     * @param bookName
     * @param bookAuthor
     * @param bookCatalogue
     * @param bookPublisher
     * @return
     */
    @DeleteMapping("/library")
    public String deleteBook(@RequestParam Optional<Integer> bookId, @RequestParam Optional<String> bookName,
                           @RequestParam Optional<String> bookAuthor, @RequestParam Optional<Integer> bookCatalogue,
                           @RequestParam Optional<String> bookPublisher) {
        try {
            return lmsService.deleteBook(bookId, bookName, bookAuthor, bookCatalogue, bookPublisher);
        } catch (Exception e) {
            logger.error("Some exception occurred in deleteBook", e);
            return null;
        }
    }

}
