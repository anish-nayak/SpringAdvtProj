package com.spring.rest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.spring.entity.AdvertiseEntity;
import com.spring.entity.UserEntity;
import com.spring.json.Advertise;
import com.spring.json.User;

@Component
public interface AdvertiseRepository extends JpaRepository<AdvertiseEntity, Long> {
	List<AdvertiseEntity> findByName(String name);

	AdvertiseEntity findByPostId(String postId);

	void deleteByPostId(String postId);

	List<AdvertiseEntity> findBySearchText(String searchText);

	List<AdvertiseEntity> findByCategory(String category);

	List<AdvertiseEntity> findByDate(LocalDate date);

	List<AdvertiseEntity> findByDateGreaterThanEqual(LocalDate date);

	List<AdvertiseEntity> findByDateLessThanEqual(LocalDate date);

	List<AdvertiseEntity> findByDateGreaterThanEqualAndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

	List<AdvertiseEntity> findByOrderByTitleAsc();

	List<AdvertiseEntity> findByOrderByTitleDesc();

	List<AdvertiseEntity> findByOrderByPostIdAsc();

	List<AdvertiseEntity> findByOrderByPostIdDesc();

	List<AdvertiseEntity> findByOrderByNameAsc();

	List<AdvertiseEntity> findByOrderByNameDesc();

}
