package com.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.AdvertiseEntity;
import com.spring.entity.MessageEntity;
import com.spring.entity.UserEntity;
import com.spring.json.Advertise;
import com.spring.rest.repository.AdvertiseRepository;
import com.spring.rest.repository.MessageRepository;
import com.spring.rest.repository.UserRepository;
import com.spring.utils.AdvertiseUtils;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdvertiseRepository advertiseRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Object save(Advertise advertise, String authToken) {
		Random random = new Random();

		UserEntity userEntity = userRepository.findBySessionId(authToken);
		if (userEntity != null) {
			String postId = Integer.toString(random.nextInt(10000));
			advertise.setPostid(postId);
			advertise.setDate(LocalDate.now());
			AdvertiseEntity advertiseEntity = advertiseRepository
					.save(AdvertiseUtils.convertAdvertiseToAdvertiseEntity(advertise));
			return AdvertiseUtils.convertAdvertiseEntityToAdvertise(advertiseEntity);
		} else {
			return "{\"result\": \"failure\",\"message\": \"Wrong Advertise Id\"}";
		}
	}

	@Override
	public Object update(Advertise advertise, String authToken, String id) {
		UserEntity userEntity = userRepository.findBySessionId(authToken);
		if (userEntity != null) {
			AdvertiseEntity advertiseEntity = advertiseRepository.findById(Long.valueOf(id)).get();
			if (advertiseEntity != null) {
				advertiseEntity.setTitle(advertise.getTitle());
				advertiseEntity.setName(advertise.getName());
				advertiseEntity.setDescription(advertise.getDescription());
				advertiseEntity.setCategory(advertise.getCategory());
				advertiseEntity.setStatus(advertise.getStatus());
				advertiseEntity.setSearchText(advertise.getSearchText());
				advertiseEntity = advertiseRepository.save(advertiseEntity);
				return AdvertiseUtils.convertAdvertiseEntityToAdvertise(advertiseEntity);
			} else
				return "{\"result\": \"failure\",\"message\": \"Wrong Advertise Id\"}";
		} else {
			return "{\"result\": \"failure\",\"message\": \"Invalid Session id\"}";
		}
	}

	@Override
	public List<Advertise> getAdvertiseBySessionId(String authToken) {
		String userName = userRepository.findBySessionId(authToken).getFirstName();
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByName(userName));
	}

	@Override
	public List<Advertise> getAdvertiseByPostId(String authToken, String postId) {
		String userName = userRepository.findBySessionId(authToken).getFirstName();
		List<Advertise> userAdvertise = AdvertiseUtils
				.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByName(userName));
		List<Advertise> requiredAdvertise = userAdvertise.stream()
				.filter(advertise -> postId.equals(advertise.getPostid())).collect(Collectors.toList());
		return requiredAdvertise;
	}

	@Override
	public boolean deleteAdvertiseByPostId(String authToken, String postId) {
		String userName = userRepository.findBySessionId(authToken).getFirstName();
		List<Advertise> userAdvertise = AdvertiseUtils
				.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByName(userName));
		Advertise advertise = AdvertiseUtils
				.convertAdvertiseEntityToAdvertise(advertiseRepository.findByPostId(postId));

		if (userAdvertise.contains(advertise)) {
			advertiseRepository.deleteByPostId(postId);
			return true;
		}
		return false;
	}

	@Override
	public List<Advertise> getAdvertiseBySearchText(String searchText) {
		return AdvertiseUtils
				.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findBySearchText(searchText));
	}

	@Override
	public List<Advertise> getAdvertiseByCategory(String category) {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByCategory(category));
	}

	@Override
	public List<Advertise> getAdvertiseByName(String name) {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByName(name));
	}

	@Override
	public Advertise getAdvertiseByPostId(String postId) {
		return AdvertiseUtils.convertAdvertiseEntityToAdvertise(advertiseRepository.findByPostId(postId));
	}

	@Override
	public List<String> getAllCategory() {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findAll()).stream()
				.map(Advertise::getCategory).distinct().collect(Collectors.toList());
	}

	@Override
	public List<Advertise> getAllAdvertiseOnDate(LocalDate date) {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByDate(date));
	}

	@Override
	public List<Advertise> getAllAdvertise() {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findAll());
	}

	@Override
	public List<Advertise> getAllAdvertiseFromDate(LocalDate date) {

		return AdvertiseUtils
				.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByDateGreaterThanEqual(date));
	}

	@Override
	public List<Advertise> getAllAdvertiseToDate(LocalDate date) {

		return AdvertiseUtils
				.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByDateLessThanEqual(date));
	}

	@Override
	public List<Advertise> getAllAdvertiseBetweenDates(LocalDate startDate, LocalDate endDate) {

		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(
				advertiseRepository.findByDateGreaterThanEqualAndDateLessThanEqual(startDate, endDate));
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByTitle() {

		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByTitleAsc());
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByDescTitle() {

		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByTitleDesc());
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByPostId() {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByPostIdAsc());
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByDescPostId() {

		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByPostIdDesc());
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByPostedBy() {
		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByNameAsc());
	}

	@Override
	public List<Advertise> getAllAdvertiseSortByDescPostedBy() {

		return AdvertiseUtils.convertAdvertiseEntityListToAdvertiseList(advertiseRepository.findByOrderByNameDesc());
	}

	@Override
	public MessageEntity addMessage(MessageEntity message) {
		String postId = message.getPostId();
		AdvertiseEntity advertiseEntity = advertiseRepository.findByPostId(postId);
		if (advertiseEntity != null) {
			List<MessageEntity> messageList = advertiseEntity.getMessageList();
			message.setPostId(postId);
			messageList.add(message);
			message = messageRepository.save(message);
			return message;
		}
		return null;
	}

}
