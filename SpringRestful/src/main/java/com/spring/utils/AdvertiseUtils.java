package com.spring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.spring.entity.AdvertiseEntity;
import com.spring.json.Advertise;

public class AdvertiseUtils {

	public static List<Advertise> convertAdvertiseEntityListToAdvertiseList(List<AdvertiseEntity> advertiseEntityList) {
		List<Advertise> advertiseList = new ArrayList<Advertise>();
		Consumer<AdvertiseEntity> consumer = (AdvertiseEntity advertiseEntity) -> advertiseList
				.add(convertAdvertiseEntityToAdvertise(advertiseEntity));
		advertiseEntityList.stream().forEach(consumer);
		return advertiseList;
	}

	public static Advertise convertAdvertiseEntityToAdvertise(AdvertiseEntity advertiseEntity) {
		return new Advertise(advertiseEntity.getId(), advertiseEntity.getTitle(), advertiseEntity.getName(),
				advertiseEntity.getCategory(), advertiseEntity.getDescription(), advertiseEntity.getPostid(),
				advertiseEntity.getStatus(), advertiseEntity.getSearchText(), advertiseEntity.getDate());
	}

	public static AdvertiseEntity convertAdvertiseToAdvertiseEntity(Advertise advertise) {
		return new AdvertiseEntity(advertise.getTitle(), advertise.getName(), advertise.getCategory(),
				advertise.getDescription(), advertise.getPostid(), advertise.getStatus(), advertise.getSearchText(),
				advertise.getDate());
	}
}
