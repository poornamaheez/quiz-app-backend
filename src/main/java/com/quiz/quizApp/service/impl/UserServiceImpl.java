package com.quiz.quizApp.service.impl;import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.quizApp.model.User;
import com.quiz.quizApp.repository.ScoreRepository;
import com.quiz.quizApp.repository.UserRepository;
import com.quiz.quizApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
    private final UserRepository userRepository;
	@Autowired
	private ScoreRepository scoreRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUserByUsername(String usernamesString) {
    	User user = userRepository.findByUsername(usernamesString);
//      System.out.println(NewConstructorTypeMunger (user.getUsername(), user.getPassword()));
      return user != null ? new User(user.getUsername(),user.getPasswordString(),user.getSalt()) : null;    
      }

    @Override
    public ResponseEntity<String> createUser(Map<String, String> user) throws NoSuchAlgorithmException {
    	System.out.println("hr");
    	byte[] salt = generateSalt();
    	String username = user.get("username");
    	String password = hashPassword(user.get("password"),salt);
    	
        User present_user = new User(username, password,bytesToHex(salt));
        
        if (getUserByUsername(present_user.getUsername()) != null) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("User name already exists");
		}
       userRepository.save(present_user);
        
       return ResponseEntity.status(HttpStatus.OK).body("Account created succesfully");
    }
    
    @Override
    public ResponseEntity<String> loginUser(Map<String, String> user) throws NoSuchAlgorithmException{
    	System.out.println("login");
    	String username = user.get("username");
    	String password = user.get("password");
    	if(username.equals("quiz-admin")&& password.equals("myPasskey")) {
    		System.out.println("heeeeeeeeeeeeeeeeeeeeeeeeeeeello");
    		return ResponseEntity.status(HttpStatus.OK).body("You are the admin");
    	}
    	User saved_user = getUserByUsername(username);
    	if(saved_user!=null) {
            boolean passwordMatches = checkPassword(password,saltFromString(saved_user.getSalt()) , saved_user.getPasswordString());
            
            if (passwordMatches) {
            	scoreRepository.deleteAll();
				return ResponseEntity.status(HttpStatus.OK).body("Login succesful");
			}
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password");
    	}
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username");
    }
    
    
    
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    // Generate a random salt
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Hash a password with a given salt using SHA-256
    public String hashPassword(String plainTextPassword, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPasswordBytes = md.digest(plainTextPassword.getBytes());

        // Convert the byte array to a hexadecimal string
        return bytesToHex(hashedPasswordBytes);
    }

    // Check if a plain text password matches a hashed password and salt
    public boolean checkPassword(String plainTextPassword, byte[] salt, String hashedPassword) throws NoSuchAlgorithmException {
        String newlyHashedPassword = hashPassword(plainTextPassword, salt);
//        System.out.println(newlyHashedPassword);
        return hashedPassword.equals(newlyHashedPassword);
    }

    // Convert a byte array to a hexadecimal string
    public String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    public byte[] saltFromString(String saltString) {
        int len = saltString.length();
        byte[] salt = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            salt[i / 2] = (byte) ((Character.digit(saltString.charAt(i), 16) << 4)
                               + Character.digit(saltString.charAt(i + 1), 16));
        }

        return salt;
    }
}