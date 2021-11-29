package com.prueba.repository;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.prueba.model.Item;

@Repository
public class ItemRepository implements RedisRepository {

	private static final String KEY = "Item";

	private RedisTemplate<String, Item> redisTemplate;
	private HashOperations hashOperations;

	public ItemRepository(RedisTemplate<String, Item> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public Map<String, Item> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public Item findById(String id) {
		return (Item) hashOperations.get(KEY, id);
	}

	@Override
	public void save(Item item) {
		hashOperations.put(KEY, UUID.randomUUID().toString(), item);

	}

	@Override
	public void delete(String id) {
		hashOperations.delete(KEY, id);

	}

}
