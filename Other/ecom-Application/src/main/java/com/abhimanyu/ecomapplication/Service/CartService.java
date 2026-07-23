package com.abhimanyu.ecomapplication.Service;

import com.abhimanyu.ecomapplication.Model.CartItem;
import com.abhimanyu.ecomapplication.Model.Product;
import com.abhimanyu.ecomapplication.Model.User;
import com.abhimanyu.ecomapplication.Repository.CartItemRepository;
import com.abhimanyu.ecomapplication.Repository.ProductRepository;
import com.abhimanyu.ecomapplication.Repository.UserRepository;
import com.abhimanyu.ecomapplication.dto.CartItemRequest;
import com.abhimanyu.ecomapplication.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

      Optional<Product> productOpt=  productRepository.findById(request.getProductId());

      if(productOpt.isEmpty()){
          return false;
      }
      Product product = productOpt.get();
      if(product.getStockQuantity()<request.getQuantity()){
          return false;
      }

      Optional<User>userOpt= userRepository.findById(Long.valueOf(userId));
      if(userOpt.isEmpty()){
          return false;
      }
      User user = userOpt.get();

      CartItem existingCartItem= cartItemRepository.findByUserAndProduct(user,product);

      if(existingCartItem!=null){
          //update the quantity

          existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
          existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
          cartItemRepository.save(existingCartItem);

      }else{
          //create new cartitem

          CartItem cartItem = new CartItem();

          cartItem.setUser(user);
          cartItem.setProduct(product);
          cartItem.setQuantity(request.getQuantity());
          cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
          cartItemRepository.save(cartItem);

      }
      return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {

        Optional<Product> productOpt=  productRepository.findById(productId);
        Optional<User>userOpt= userRepository.findById(Long.valueOf(userId));

        if(productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return  true;
        }

        return false;

    }

    public List<CartItem> getUserCart(String userId) {

        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userId) {

        userRepository.findById(Long.valueOf(userId)).ifPresent(CartItemRepository::deleteByUser
        );

    }
}
