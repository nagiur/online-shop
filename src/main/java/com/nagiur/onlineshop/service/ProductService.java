package com.nagiur.onlineshop.service;

import com.nagiur.onlineshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();

}
