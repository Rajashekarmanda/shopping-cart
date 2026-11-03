package com.codewithraj.shoppingcart.service.user;

import com.codewithraj.shoppingcart.dto.UserDTO;
import com.codewithraj.shoppingcart.model.User;
import com.codewithraj.shoppingcart.request.CreateUserRequest;
import com.codewithraj.shoppingcart.request.UpdateUserRequest;

public interface IUserService {

    User createUser(CreateUserRequest request);

    User getUserById(Long id);

    User updateUser(Long id, UpdateUserRequest request);

    void deleteUserById(Long id);

    UserDTO convertToDTO(User user);
}