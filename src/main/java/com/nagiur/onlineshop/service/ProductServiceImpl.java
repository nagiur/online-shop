package com.nagiur.onlineshop.service;

import com.nagiur.onlineshop.dto.ProductDTO;
import com.nagiur.onlineshop.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAllProductSortedByName() {
        return productRepository.findAllProducts().stream().sorted(Comparator.comparing(ProductDTO::getName)).collect(Collectors.toList());
    }
}
