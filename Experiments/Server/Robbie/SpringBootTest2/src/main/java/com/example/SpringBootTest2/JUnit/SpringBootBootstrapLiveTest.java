package com.example.SpringBootTest2.JUnit;

import com.example.SpringBootTest2.Book;
import org.springframework.http.MediaType;

import javax.xml.ws.Response;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "https://localhost:8081/api/books";

    private Book createRandomBook() {
        Book book = new Book();
        book.setTitle("HEEHEE");
        book.setAuthor("MJ");
        return book;
    }

    //private String createBookAsUri(Book book) {
    //    Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_ROOT);
    //    return API_ROOT + "/" + response.jsonPath().get("id");
    //}

}
