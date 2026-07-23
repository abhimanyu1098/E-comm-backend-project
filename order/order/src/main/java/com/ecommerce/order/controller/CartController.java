package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request){

        if(!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product out of stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void>removeFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable String productId){

        boolean deleted= cartService.deleteItemFromCart(userId,productId);

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>>getCartItems(@RequestHeader("X-User-ID") String userId){

        return ResponseEntity.ok(cartService.getUserCart(userId));
    }


}
