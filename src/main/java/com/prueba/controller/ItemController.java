package com.prueba.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.exception.NotFoundException;
import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;
import com.prueba.service.IItemService;

@RestController
public class ItemController {
	private Logger log = LoggerFactory.getLogger(ItemController.class);

	private ItemRepository itemRepository;

	private IItemService itemServiceImpl;

	public ItemController(ItemRepository itemRepository, IItemService itemServiceImpl) {
		this.itemRepository = itemRepository;
		this.itemServiceImpl = itemServiceImpl;
	}

	@GetMapping("/items")
	public Map<String, Item> findAll() {
		return itemRepository.findAll();
	}

	@GetMapping("/items/{id}")
	public Item findById(@PathVariable String id) {
		return itemRepository.findById(id);
	}

	@GetMapping("/items/ml/{id}")
	public ResponseEntity<Item> findByIdMl(@PathVariable String id) {
		try {
			log.info("Inicia consulta controller {}", id);
			Item itemResult = itemServiceImpl.findItemMl(id);
			log.info("Finaliza consulta controller {}", itemResult);
			return ResponseEntity.ok(itemResult);
		} catch (NotFoundException e) {
			log.error("Error en consulta controller {} exception : {}", e.getMessage(), e);
			return ResponseEntity.notFound().build();
		}
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
