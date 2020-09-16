package com.revature.services;


import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Constitutes the SERVICE LAYER fro users. concerned with validating all user
 * input before being sent to the database.
 */
public class UserService {
    private UserRepository userRepo = new UserRepository();

    public List<User> getAllUsers(){
        List<User> users = userRepo.getAllusers();
        if (users.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return users;
    }


    public User authenticate(String username, String password){

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")){
            throw new InvalidRequestException("Invalid credentials provided");
        }
        return userRepo.getAUserByUsernameAndPassword(username,password)
                .orElseThrow(AuthenticationException::new);
    }


    public void register(User newUser) {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user field values provided during registration!");
        }
        Optional<User> existingUser = userRepo.getAUserByUsername(newUser.getUsername());

        if (existingUser.isPresent()) {
            // TODO implement a custom ResourcePersistenceException
            throw new ResourcePersistenceException("Username is already in use");
        }
        Optional<User> existingUserEmail = userRepo.getAUserByEmail(newUser.getEmail());
        if (existingUserEmail.isPresent()) {
            // TODO implement a custom ResourcePersistenceException
            throw new ResourcePersistenceException("Email is already in use");
        }
        newUser.setUserRole(Role.EMPLOYEE.ordinal() + 1);
        userRepo.addUser(newUser);
        System.out.println(newUser);
//        app.setCurrentUser(newUser);
    }

    public void update(User newUser) {
        if (userRepo.updateAUser(newUser)){
            System.out.println(newUser);
        }
        else {
            throw new ResourcePersistenceException("There was a problem trying to update the user");
        }
    }


    public User getUserById(int id) {
        if (id <= 0){
            throw new InvalidRequestException("tHE PROVIDED ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        return userRepo.getAUserById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public boolean deleteUserById(int id) {
        if (id <= 0){
            throw new InvalidRequestException("tHE PROVIDED ID CANNOT BE LESS THAN OR EQUAL TO ZERO");
        }
        return userRepo.deleteAUserById(id);
    }
    public User getUserByEmail(String email) {
        if (email == null || email.trim().equals("")){
            throw new InvalidRequestException("tHE PROVIDED EMAIL CANNOT BE BLANK");
        }
        return userRepo.getAUserByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }
    public List<User> getUsersByRole(String role) {
        if (role == null || role.isEmpty() || role.trim().equals("")){
            throw new InvalidRequestException("tHE PROVIDED ROLE DOES NOT EXIST");
        }
        List<User> users = userRepo.getAllUsersByRole(Role.getByName(role));
        if (users.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return users;
    }

    public boolean isUsernameAvailable(String username) {
        User user = userRepo.getAUserByUsername(username).orElse(null);
        return user == null;
    }

    public boolean isEmailAvailable(String email) {
        User user = userRepo.getAUserByEmail(email).orElse(null);
        return user == null;
    }

    /**
     * Validates that the given user and its fields are valid (not null or empty strings). Does
     * not perform validation on id or role fields.
     *
     * @param user
     * @return true or false depending on if the user was valid or not
     */
    public boolean isUserValid(User user) {
        if (user == null) return false;
        if (user.getFirstname() == null || user.getFirstname().trim().equals("")) return false;
        if (user.getLastname() == null || user.getLastname().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }
}
