package com.abhimanyu.ecomapplication.Service;

import com.abhimanyu.ecomapplication.Model.Product;
import com.abhimanyu.ecomapplication.Repository.ProductRepository;
import com.abhimanyu.ecomapplication.dto.ProductRequest;
import com.abhimanyu.ecomapplication.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public ProductResponse addProduct(ProductRequest productRequest) {

        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct=productRepository.save(product);
        return mapToProductResponse(savedProduct);



    }

    private ProductResponse mapToProductResponse(Product savedProduct) {

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setActive(savedProduct.getActive());
        return productResponse;

    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {


        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

            return productRepository.findById(id)
                    .map(existingProduct->{
                        updateProductFromRequest(existingProduct,productRequest);
                        Product savedProduct=productRepository.save(existingProduct);
                        return mapToProductResponse(savedProduct);
                    });
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }

    public boolean deleteProduct(Long id) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);

    }

    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
