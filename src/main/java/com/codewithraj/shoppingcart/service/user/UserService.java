package com.codewithraj.shoppingcart.service.user;

import com.codewithraj.shoppingcart.dto.UserDTO;
import com.codewithraj.shoppingcart.exceptions.AlreadyExistsException;
import com.codewithraj.shoppingcart.exceptions.ResourceNotFoundException;
import com.codewithraj.shoppingcart.model.User;
import com.codewithraj.shoppingcart.repository.UserRepository;
import com.codewithraj.shoppingcart.request.CreateUserRequest;
import com.codewithraj.shoppingcart.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(CreateUserRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(email + " is already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(email);
        user.setPassword(request.getPassword()); // plain text for now; BCrypt in Phase 7
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        User existing = getUserById(id); // 404 if missing
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUserById(Long id) {
        User existing = getUserById(id); // 404 if missing
        userRepository.delete(existing);
    }

    @Override
    public UserDTO convertToDTO(User user) {
        // password is deliberately never copied
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}