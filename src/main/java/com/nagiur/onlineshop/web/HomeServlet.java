package com.nagiur.onlineshop.web;

import com.nagiur.onlineshop.dto.ProductDTO;
import com.nagiur.onlineshop.repository.DummyProductServiceImpl;
import com.nagiur.onlineshop.service.ProductService;
import com.nagiur.onlineshop.service.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/home", loadOnStartup = 1)
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(HomeServlet.class);
    private ProductService productService = new ProductServiceImpl(new DummyProductServiceImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ProductDTO> allProducts = productService.findAllProductSortedByName();

        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);

    }
}
