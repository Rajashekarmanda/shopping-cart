package com.codewithraj.shoppingcart.controller;

import com.codewithraj.shoppingcart.dto.UserDTO;
import com.codewithraj.shoppingcart.exceptions.AlreadyExistsException;
import com.codewithraj.shoppingcart.exceptions.ResourceNotFoundException;
import com.codewithraj.shoppingcart.model.User;
import com.codewithraj.shoppingcart.response.ApiResponse;
import com.codewithraj.shoppingcart.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user) {
        try{
            User newUser = userService.addUser(user);
            UserDTO dtoUser = userService.convertToDTO(newUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("User added successfully", dtoUser));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUser(@RequestParam Long id){
        try{
            User user = userService.getUser(id);
            UserDTO dtoUser = userService.convertToDTO(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User found", dtoUser));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user){
        try{
            User newUser = userService.updateUser(user);
            UserDTO dtoUser = userService.convertToDTO(newUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User updated successfully", dtoUser));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}



















