package com.abhimanyu.ecomapplication.Service;

import com.abhimanyu.ecomapplication.Model.Address;
import com.abhimanyu.ecomapplication.Model.User;
import com.abhimanyu.ecomapplication.Repository.UserRepository;
import com.abhimanyu.ecomapplication.dto.AddressDTO;
import com.abhimanyu.ecomapplication.dto.UserRequest;
import com.abhimanyu.ecomapplication.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    //List<User> users = new ArrayList<>();
    //Long ID=1L;
    private final UserRepository userRepository;
    public List<UserResponse> fetchUsers() {

        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUsers( UserRequest userRequest) {
        //user.setId(ID++);
        User user = new User();
        updateUserfromRequest(user,userRequest);
        userRepository.save(user);

    }



    public Optional<UserResponse> fetchId(Long id) {
        return userRepository.findById(id).map(this::mapToUserResponse);

    }

    public boolean update(Long id,UserRequest updateUserRequest) {

        return userRepository.findById(id)
                    .map(existingUser->{
                    updateUserfromRequest(existingUser,updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setZip(user.getAddress().getZip());
            addressDTO.setState(user.getAddress().getState());
            userResponse.setAddress(addressDTO);
        }
        return userResponse;

    }

    private void updateUserfromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZip(userRequest.getAddress().getZip());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }

    }
}
