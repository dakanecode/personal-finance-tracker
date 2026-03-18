package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.UserRequestDTO;
import com.personalfinancetracker.dto.UserResponseDTO;
import com.personalfinancetracker.entity.User;
import com.personalfinancetracker.exception.EmailAlreadyExistsException;
import com.personalfinancetracker.exception.ResourceNotFoundException;
import com.personalfinancetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    // need the repository
    private final UserRepository userRepository;
    // create a user
    public UserResponseDTO createUser(UserRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
             throw new EmailAlreadyExistsException("Email exists");
        }

        // It is the request:
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .gender(dto.getGender())
                .build();
        // request saved:
        User saved = userRepository.save(user);
        // return the response
        return mapToResponseDTO(saved);

    }


//getUserById
    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with: " + id + " not found"));

        return mapToResponseDTO(user);

    }
    // get by email
    public UserResponseDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with: " + email + " not found"));
        return mapToResponseDTO(user);
    }

    // get all users: List of users
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    // delete user by id
    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        userRepository.deleteById(id);
    }

    // update user
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto){
        // get user by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with: " + id + " not found"));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.getGender());

        User saved = userRepository.save(user);
        return mapToResponseDTO(saved);
    }

    // helper function
    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
