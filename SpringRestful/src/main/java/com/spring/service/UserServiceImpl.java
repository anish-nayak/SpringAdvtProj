package com.spring.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.UserEntity;
import com.spring.json.User;
import com.spring.rest.repository.UserRepository;
import com.spring.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		UserEntity userEntity = userRepository.save(UserUtils.convertUserToUserEntity(user));
		return UserUtils.convertUserEntityToUser(userEntity);
	}

	public List<User> getAllUsers() {
		List<UserEntity> userEntityList = userRepository.findAll();
		return UserUtils.convertUserEntityListToUserList(userEntityList);
	}

	public User getUserById(String id) {
		UserEntity userEntity = userRepository.findById(Long.valueOf(id)).get();
		return UserUtils.convertUserEntityToUser(userEntity);
	}

	public User update(User user, String id) {
		UserEntity userEntity = userRepository.findById(Long.valueOf(id)).get();
		if (userEntity != null) {
			userEntity.setFirstName(user.getFirstName());
			userEntity.setUserName(user.getUserName());
			userEntity.setPassword(user.getPassword());
			userEntity = userRepository.save(userEntity);
			return UserUtils.convertUserEntityToUser(userEntity);
		}
		return null;
	}

	public boolean delete(String id) {
		if (userRepository.existsById(Long.valueOf(id))) {
			userRepository.deleteById(Long.valueOf(id));
			return true;
		}
		return false;
	}

	public String login(User user) {
		String sessionId = null;
		Random random = new Random();
		User newUser = UserUtils.convertUserEntityToUser(userRepository.findByUserName(user.getUserName()));

		if (newUser.getUserName().equals(user.getUserName())) {
			if (newUser.getPassword().equals(user.getPassword())) {
				sessionId = Integer.toString(random.nextInt(10000));
				UserEntity userEntity = userRepository.findById(Long.valueOf(newUser.getId())).get();
				userEntity.setSessionId(sessionId);
				userEntity = userRepository.save(userEntity);
				return "{\"result\": \"success\",\"auth-token\":\"" + sessionId + "\"}";
			} else {
				return "{\"result\": \"invalid Password\"}";
			}
		} else
			return "{\"result\": \"Invalid User\"}";

	}

	public String logout(String authToken) {
		if (authToken.equals(null)) {
			return "{\"result\": \"Invalid Session-Id\"}";
		} else {
			UserEntity userEntity = userRepository.findBySessionId(authToken);
			userEntity.setSessionId(null);
			userEntity = userRepository.save(userEntity);
			return "{\"result\": \"Sucess\"}";
		}
	}

}
