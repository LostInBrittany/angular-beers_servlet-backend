package org.lostinbrittany.beers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lostinbrittany.beers.dao.BeerDAO;
import org.lostinbrittany.beers.model.Beer;

/**
 * Servlet implementation class BeerAddServlet
 */
@WebServlet("/BeerCreate")
public class BeerCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeerCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setStatus(400);		
		response.getWriter().append("Only POST method allowed on BeerCreate");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean validParameters = true;
		StringBuilder validateMsgs = new StringBuilder();
		
		String name = request.getParameter("name");
		if (!Beer.isValidNameParam(name)) {
			validParameters = false;
			validateMsgs.append("Invalid parameter: name\n");
		}
		
		String alcohol = request.getParameter("alcohol");
		if (!Beer.isValidAlcoholParam(alcohol)) {
			validParameters = false;
			validateMsgs.append("Invalid parameter: alcohol\n");
		}

		String img = request.getParameter("img");
		if (!Beer.isValidImgParam(img)) {
			validParameters = false;
			validateMsgs.append("Invalid parameter: img\n");
		}
		
		String description = request.getParameter("description");
		if (!Beer.isValidDescriptionParam(description)) {
			validParameters = false;
			validateMsgs.append("Invalid parameter: description\n");
		}
		// Optional beer attributes
		String availability = Beer.generateValidGenericParam(request.getParameter("availability"));
		String brewery = Beer.generateValidGenericParam(request.getParameter("brewery"));
		String serving = Beer.generateValidGenericParam(request.getParameter("serving"));
		String style = Beer.generateValidGenericParam(request.getParameter("style"));		
		String label = Beer.generateValidUrlParam(request.getParameter("label"));	
		
		if (!validParameters) {
			response.setStatus(400);			
			response.getWriter().append("{ \"status\":\"ko\", \"msg\": \""+ validateMsgs.toString()+"\"}");
			return;
		}
		
		Beer beer = new Beer();
		
		beer.setName(name);
		beer.setAlcohol(Double.parseDouble(alcohol));
		beer.setImg(img);
		beer.setDescription(description);
		beer.setAvailability(availability);
		beer.setBrewery(brewery);
		beer.setServing(serving);
		beer.setStyle(style);
		beer.setLabel(label);
		

		if (BeerDAO.getInstance().hasBeer(beer.getId())) {
			response.setStatus(400);
			response.getWriter().append("{\"status\":\"ko\", \"msg\":\"Document already in base\"}");
			return;
		}
		
		BeerDAO.getInstance().createBeer(beer);		
		response.getWriter().append("{\"status\":\"ok\", \"msg\":\"Beer inserted in base\"}");
		
		
	}

}
