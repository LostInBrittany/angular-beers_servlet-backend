package org.lostinbrittany.beers.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lostinbrittany.beers.dao.BeerDAO;

/**
 * Servlet implementation class BeerDeleteServlet
 */
@WebServlet("/delete")
public class BeerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeerDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setStatus(400);		
		response.getWriter().append("Only POST method allowed on BeerDelete");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("json/application; charset=utf-8");
		
		String id = request.getParameter("id");
			
		
		if (!BeerDAO.getInstance().hasBeer(id)) {
			response.setStatus(400);
			response.getWriter().append("{\"status\":\"ko\", \"msg\":\"Beer doesn't exists in base\"}");
			return;
		}
		
		BeerDAO.getInstance().deleteBeer(id);;		
		response.getWriter().append("{\"status\":\"ok\", \"msg\":\"Beer deleted from base\"}");
		
	}

}
