package com.nagiur.onlineshop.repository;

import com.nagiur.onlineshop.dto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProducts();
}