package com.pinguela.yourpc.web.controller;

import static com.pinguela.yourpc.web.util.Parameters.NAME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@SuppressWarnings("serial")
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(ProductServlet.class);
	
	private ProductService service;
       
    public ProductServlet() {
        super();
        service = new ProductServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(NAME));
		
		try {
			Results<Product> results = service.findBy(criteria, 1, 10);
			request.setAttribute("results", results);
			request.getRequestDispatcher("/product/product-results-view.jsp").forward(request, response);
		} catch (YPCException e) {
			logger.error(e.getMessage(), e);
		} 
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
