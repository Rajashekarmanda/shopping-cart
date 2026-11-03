package com.codewithraj.shoppingcart.service.user;

import com.codewithraj.shoppingcart.dto.UserDTO;
import com.codewithraj.shoppingcart.model.User;

public interface IUserService {

    public User addUser(User user);

    public User updateUser(User user);

    public User getUser(Long id);

    public void deleteUser(Long id);

    public UserDTO convertToDTO(User user);

}
