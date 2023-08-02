package controllers;

import java.io.BufferedReader;
//import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.BookDAO;
import models.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Servlet implementation class bookapis
 */
@WebServlet("/bookapis")
public class bookapis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String format = request.getHeader("Accept");
		if (request.getParameter("id") != null) {

			int id = Integer.parseInt(request.getParameter("id"));
			PrintWriter out = response.getWriter();
			BookDAO dao = new BookDAO();
			Book oneBook = dao.getBookByID(id);
			Gson gson = new Gson();
		
			// String format = request.getParameter("format");
			//
			// // determine which format the user want the data to be returned
			// if (format.equals("json")) {
			// response.setContentType("application/json");
			out.write(gson.toJson(oneBook));
			// }

			
		} else {
			PrintWriter out = response.getWriter();
			BookDAO dao = new BookDAO();
			ArrayList<Book> allBooks = dao.getAllBooks();
			if (format.equals("application/json")) {
				
			response.setContentType("application/json");

			Gson gson = new Gson();
			//PrintWriter out = response.getWriter();
			// String format = request.getParameter("format");
			//
			// // determine which format the user want the data to be returned
			// if (format.equals("json")) {
			// response.setContentType("application/json");
			String GSON = gson.toJson(allBooks);
			out.write(GSON);
			out.close();
	
			// }
		}
			else if (format.equals("application/xml")){
				response.setContentType("application/xml");
				//Writer out = response.getWriter();
				
				BookList bl = new BookList(allBooks);
			    StringWriter sw = new StringWriter();
			    try {
			     	JAXBContext context = JAXBContext.newInstance(BookList.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				    m.marshal(bl, sw);
				    out.write(sw.toString());
				    out.close();
				
	
			     } catch (Exception e) {
					     e.printStackTrace();
				
			     }
			    
		   }
			
			
			else {
				response.setContentType("text/plain");
				String text = allBooks.toString();
				out.write(text);
			}
			
			
			/*
			 * System.out.println("in the book controller"); // // BookDAO dao = new
			 * BookDAO(); ArrayList<Book> allCons = dao.getAllBooks();
			 * System.out.println(allCons.size()); request.setAttribute("books", allCons);
			 */
     //		RequestDispatcher rd = request.getRequestDispatcher("book.jsp");
//			rd.include(request, response);
		}
		
		
//		response.sendRedirect("./bookapis");
		
	      
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String getinfo = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		String contentType = request.getHeader("Content-Type");

		Book book = null;
		
		if (contentType.contains("application/json")) {
			Gson gson = new Gson();
			book = gson.fromJson(getinfo, Book.class);
		}
		
		else if(contentType.contains("application/xml")) {
	
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Book.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				 StringReader sr = new StringReader(getinfo);
		  		book = (Book) jaxbUnmarshaller.unmarshal(sr);
		  		
			} catch (JAXBException e) {

				e.printStackTrace();
			}
			
		}
		
//		else {
//			book = parsePlaintext(getinfo);
//			
//		}
//		
		if (book!=null) {
			Book book1 = new Book(0, book.getTitle(), book.getAuthor(), book.getCharacters(), book.getDate(), book.getGenres(), book.getSynopsis());
			
			
			try {
				BookDAO dao = new BookDAO();
				dao.insertBook(book1);
				//Gson gson = new Gson();
				PrintWriter out = response.getWriter();
				out.write("Book Inserted");
				//
				// System.out.println("id" + " title" + " author" + " date" + " genres" + "
				// characters" + " synopsis");

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e);
			}
//			response.sendRedirect("./bookapis");
			
		      
		}
		
		
		//BookDAO dao = new BookDAO();
		// Code for inserting book
		// int id_edit = Integer.parseInt(request.getParameter("id"));
//		String title = request.getParameter("title");
//		String author = request.getParameter("author");
//		String date = request.getParameter("date");
//		String genres = request.getParameter("genres");
//		String characters = request.getParameter("characters");
//		String synopsis = request.getParameter("synopsis");
		//
		//Book insertBook = new Book(0, title, author, date, genres, characters, synopsis);
		//
		
	}

	


	private String extraCode(HttpServletRequest request) throws IOException {
		StringBuilder ds = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;

		while ((line = reader.readLine()) != null) {
			ds.append(line);
		}
		return ds.toString();
	}

	private void respondWithJson(HttpServletResponse response, String json) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
	
	
	
	
@Override
  protected void doPut(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException { 
	Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	BookDAO dao = new BookDAO();
	 
	 int id = Integer.parseInt(request.getParameter("id")); 
	 String title = request.getParameter("title"); 
	 String author = request.getParameter("author"); 
	 String date = request.getParameter("date");
	 String genres = request.getParameter("genres");
	 String characters = request.getParameter("characters"); 
	 String synopsis = request.getParameter("synopsis"); 
	 Book book = new Book(id, title, author, date, genres, characters, synopsis);
	 String info = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
	 
	 try { 
     //String requestBody = extraCode(request); 
		 
	 Book bookUpdate = gson.fromJson(info, Book.class);
	 dao.updateBook(bookUpdate); 
	 respondWithJson(response, gson.toJson(bookUpdate)); 
	
	 } catch (SQLException e) { e.printStackTrace();
	  System.out.println(e); 
	  }

	 request.getRequestDispatcher("book.jsp").forward(request, response);
//	response.sendRedirect("./bookapis");
	 }
  
  
  
@Override
  protected void doDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException { 
	Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	BookDAO dao = new BookDAO();
	

	int id = Integer.parseInt(request.getParameter("id"));
	
		try {
			Book bk = dao.getBookByID(id);
		
				dao.deleteBook(bk);
				PrintWriter out = response.getWriter();
				out.write("Book Deleted");
		
			} catch (SQLException e) {
				
			
			e.printStackTrace();
		
			} 
//		response.sendRedirect("./bookapis");
		
      
	
}
}
