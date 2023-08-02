package controllers;

import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashMap;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import com.google.gson.*;

import models.Book;
import database.BookDAO;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "books")

	public class BookList {
	@XmlElement(name = "book")
	
	private List<Book> booksList;
	
	public BookList() {}
	
	public BookList(List<Book> booksList) {
	
		this.booksList = booksList;
	}
	public List<Book> getBooksList() {
	
		return booksList;
	}
	
	public void setBooksList(List<Book> booksList) {
	
		this.booksList = booksList;
	}


}
