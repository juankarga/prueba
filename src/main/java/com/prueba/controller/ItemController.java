package com.prueba.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;

@RestController
public class ItemController {

	private ItemRepository itemRepository;

	public ItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping("/items")
	public Map<String, Item> findAll() {
		return itemRepository.findAll();
	}

	@GetMapping("/items/{id}")
	public Item findById(@PathVariable String id) {
		return itemRepository.findById(id);
	}

	@PostMapping("/items")
	public void createItem(@RequestBody Item item) {
		itemRepository.save(item);
	}

	@DeleteMapping("/items/{id}")
	public void deleteItem(@PathVariable String id) {
		itemRepository.delete(id);
	}
}
