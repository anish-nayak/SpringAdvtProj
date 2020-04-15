package com.spring.service;

import java.time.LocalDate;
import java.util.List;

import com.spring.entity.MessageEntity;
import com.spring.json.Advertise;
import com.spring.json.User;

public interface AdvertiseService {

	Object save(Advertise advertise, String authToken);

	Object update(Advertise advertise, String authToken, String id);

	List<Advertise> getAdvertiseBySessionId(String authToken);

	List<Advertise> getAdvertiseByPostId(String authToken, String postId);

	boolean deleteAdvertiseByPostId(String authToken, String postId);

	List<Advertise> getAdvertiseBySearchText(String searchText);

	List<Advertise> getAdvertiseByCategory(String category);

	List<Advertise> getAdvertiseByName(String name);

	Advertise getAdvertiseByPostId(String postId);

	List<String> getAllCategory();

	List<Advertise> getAllAdvertiseOnDate(LocalDate date);

	List<Advertise> getAllAdvertise();

	List<Advertise> getAllAdvertiseFromDate(LocalDate date);

	List<Advertise> getAllAdvertiseToDate(LocalDate date);

	List<Advertise> getAllAdvertiseBetweenDates(LocalDate startDate, LocalDate endDate);

	List<Advertise> getAllAdvertiseSortByTitle();

	List<Advertise> getAllAdvertiseSortByDescTitle();

	List<Advertise> getAllAdvertiseSortByPostId();

	List<Advertise> getAllAdvertiseSortByDescPostId();

	List<Advertise> getAllAdvertiseSortByDescPostedBy();

	List<Advertise> getAllAdvertiseSortByPostedBy();

	MessageEntity addMessage(MessageEntity message);

}
