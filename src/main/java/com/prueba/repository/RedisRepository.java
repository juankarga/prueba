package com.prueba.repository;

import java.util.Map;

import com.prueba.model.Item;

public interface RedisRepository {

	Map<String, Item> findAll();

	Item findById(String id);

	void save(Item item);

	void delete(String id);
}
