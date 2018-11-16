package com.pelagusit.store.repository;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.User;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);
    
    @Query("select u from User u where u.activated = false and u.createdDate > ?1")
    List<User> findNotActivatedUsersByCreationDateBefore(DateTime dateTime);

    @Query("select u from User u")
	Page<User> getAllUsers(Pageable page);

    @Query("select u from User u where u.email like %?1%")
    Page<User> getUsersByEmail(String email,Pageable page);

    @Query("select u from User u where u.login like %?1%")
	Page<User> getUsersByUserName(String userName, Pageable pageable);
    
    @Query("select u from User u where u.login like %?1% and u.email like %?2% ")
	Page<User> getUsersByUserNameAndEmail(String userName, String userEmail, Pageable pageable);
    
    
    @Query("select u from User u where u.login = ?1")
    User getUserByLogin(String login);
    
    @Query("select u from User u where u.login = ?1")
	public List <User> findUser(String username);


}
