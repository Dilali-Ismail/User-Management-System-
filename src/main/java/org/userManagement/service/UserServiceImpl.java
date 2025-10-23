package org.userManagement.service;

import org.springframework.transaction.annotation.Transactional;
import net.bytebuddy.asm.Advice;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.userManagement.model.User;
import org.userManagement.repository.UserRepository;
import org.w3c.dom.DOMImplementationSource;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
  private final UserRepository userRepository  ;

  @Autowired
    public UserServiceImpl(UserRepository userRepository){
      this.userRepository = userRepository;
  }

    @Override
  public User createUser(User user){
      return userRepository.save(user);
  }
    @Override
  public List<User> getAllUsers(){
      return userRepository.findAll();
  }
    @Override
  public Optional<User> getUserById(Long id)
  {
        return userRepository.findById(id);
  }
    @Override
  public User updateUser(Long id, User userDetails){
      User existingUser = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

      existingUser.setName(userDetails.getName());
      existingUser.setEmail(userDetails.getEmail());
      existingUser.setRole(userDetails.getRole());
      existingUser.setActive(userDetails.isActive());
      return userRepository.save(existingUser);
  }
    @Override
  public void deleteUser(Long id){
      if(!userRepository.existsById(id)){
          throw new RuntimeException("User not found with id: " + id);
      }

      userRepository.deleteById(id);
  }
 @Override
  public  Optional<User> getUserByEmail(String email){
      return userRepository.findByEmail(email);
  }



}
