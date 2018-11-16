package com.pelagusit.store.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.AuthoritiesConstants;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.UserService;
import com.pelagusit.store.web.rest.dto.UserDTO;
import com.pelagusit.store.web.rest.util.RequestProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/app")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;
    
	@Autowired
	private UserService userService;

	public UserService getService() {
		return userService;
	}

    /**
     * GET  /rest/users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/rest/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public User getUser(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to get User : {}", login);
        User user = userRepository.findOne(login);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }
    
	@RequestMapping(value = "/rest/users/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> getAll(String userName, String userEmail, int count, int page, HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageable = new PageRequest(page - 1, count, sort);
		Page<User> users = null;

		if ((userName == null || userName.isEmpty()) && (userEmail == null || userEmail.isEmpty())) {
			users = userRepository.getAllUsers(pageable);
		
		} else if (userName == null || userName.isEmpty()) {

			users = userRepository.getUsersByEmail(userEmail, pageable);

		} else if (userEmail == null || userEmail.isEmpty()) {
			users = userRepository.getUsersByUserName(userName, pageable);

		} else {
			users = userRepository.getUsersByUserNameAndEmail(userName, userEmail, pageable);
		}

		return users;
	}
	@RequestMapping(value = "/rest/user/{login}", method = RequestMethod.GET, produces = "application/json")
	public User get(@PathVariable String login, HttpServletRequest request, HttpServletResponse response) {
		 User user = userRepository.findOne(login);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		return user;
	}

	@RequestMapping(value = "/rest/user/removeUser/{login}", method = RequestMethod.DELETE, produces = "application/json")
	public void get(@PathVariable String login, HttpServletResponse response) {
		 User user = userRepository.findOne(login);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
			userRepository.delete(user);
	
	}
	
	@RequestMapping(value = "/rest/user/updateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateFirma(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) 
	{
	//	String username = user.getLogin();
		/*if(userService.isOverlap(username)) {
			 response.setStatus(HttpServletResponse.SC_CONFLICT);
		}*/
		if(user==null)
		{
			 response.setStatus(HttpServletResponse.SC_CONFLICT);
			 return ;
		}
/*		User checkUser = userRepository.getUserByLogin(user.getLogin());
		if(checkUser!=null){
			 response.setStatus(HttpServletResponse.SC_CONFLICT);
			 return ;
		}*/
					
		userService.updateUserInformation(user.getLogin(),user.getFirstName(),user.getLastName(), user.getEmail());
	}
	
	@RequestMapping(value = "/rest/user/saveUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveFirma(@RequestBody UserDTO user, HttpServletRequest request, HttpServletResponse response) 
	{
	//	String username = user.getLogin();
		/*if(userService.isOverlap(username)) {
			 response.setStatus(HttpServletResponse.SC_CONFLICT);
		}*/
		
		
		 User checkUser = userRepository.getUserByLogin(user.getLogin());
		  if(checkUser!=null){
		  response.setStatus(HttpServletResponse.SC_CONFLICT); return ; }
		 
		userService.createUserInformation(user.getLogin(), user.getPassword(), user.getFirstName(),user.getLastName(),user.getEmail(),user.getLangKey(),user.isActivated());
		
	}
	
	
}