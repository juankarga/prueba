package com.prueba.repository;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.prueba.model.Item;

@Repository
public class ItemRepository implements RedisRepository {

	private static final String KEY = "Item";

	@Value("${redis.ttl}")
	private int redisTtl;

	private RedisTemplate<String, Item> redisTemplate;

	private ValueOperations<String, Item> valueOperations;

	public ItemRepository(RedisTemplate<String, Item> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public Item findById(String id) {
		return (Item) valueOperations.get(buildKey(id));
	}

	@Override
	public void save(Item item) {
		valueOperations.set(buildKey(item.getId()), item, redisTtl, TimeUnit.SECONDS);
	}

	private String buildKey(String itemId) {
		return KEY.concat(":").concat(itemId);
	}

}
