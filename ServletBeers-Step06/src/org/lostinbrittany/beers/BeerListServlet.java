package org.lostinbrittany.beers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class BeerListServlet
 */
@WebServlet("/beers")
public class BeerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeerListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MongoClient mongoClient = new MongoClient();
		
		MongoDatabase database = mongoClient.getDatabase("beers");
		
		MongoCollection<Document> beers = database.getCollection("beers");
		
		
		/*
		   {
		    "alcohol": 6.8,
		    "description": "Affligem Blonde, the classic clear blonde abbey ale, with a gentle roundness and 6.8% alcohol. Low on bitterness, it is eminently drinkable.",
		    "id": "AffligemBlond",
		    "img": "/img/AffligemBlond.jpg",
		    "name": "Affligem Blond"
		  },
		 */
		StringBuilder sb = new StringBuilder("[");
		for (Document beer : beers.find()) {
			
			
			sb.append("{")
				.append("\"alcohol\":").append(beer.get("alcohol").toString()).append(",")
				.append("\"id\":\"").append(beer.getString("id")).append("\",")
				.append("\"img\":\"").append(beer.getString("img")).append("\",")
				.append("\"name\":\"").append(beer.getString("name")).append("\",")
				.append("\"description\":\"").append(beer.getString("description")).append("\"")
				.append("},");
				
			System.out.println(beer.getString("name"));
		}
		sb.deleteCharAt(sb.length()-1).append("]");

		response.getWriter().append(sb.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
