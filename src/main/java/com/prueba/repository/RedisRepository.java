package com.prueba.repository;

import com.prueba.model.Item;

public interface RedisRepository {

	Item findById(String id);

	void save(Item item);
}
