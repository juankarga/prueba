package com.prueba.service.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.prueba.exception.NotFoundException;
import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;
import com.prueba.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	private RestTemplate restTemplate;

	private ItemRepository itemRepository;

	public ItemServiceImpl(RestTemplate restTemplate, ItemRepository itemRepository) {
		super();
		this.restTemplate = restTemplate;
		this.itemRepository = itemRepository;
	}

	@Override
	public List<String> calculate(Map<String, Float> items, Float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemMl(String itemId) throws NotFoundException {

		try {
			log.info("Iniciando consulta del item {}", itemId);

			Optional<Item> itemOptional = findItemCache(itemId);
			if (itemOptional.isEmpty()) {
				JsonNode result = invokeApiMl(itemId);
				Item itemResult = mapItemResult(result);
				log.info("Resultado info del item {} resultado {}", itemId, itemResult);
				saveInCache(itemResult);
				return itemResult;
			} else {
				return itemOptional.get();
			}

		} catch (Exception e) {
			throw new NotFoundException("No se encontro el item indicado", e);
		}
	}

	private Item mapItemResult(JsonNode result) {
		Item itemResult = new Item();
		itemResult.setId(result.get("id").asText());
		itemResult.setAmount(result.get("price").asDouble());
		return itemResult;
	}

	private JsonNode invokeApiMl(String itemId) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString("https://api.mercadolibre.com/items")
				.pathSegment(itemId);
		URI uri = urlBuilder.build().encode().toUri();
		log.info("URL {}", uri.toString());
		JsonNode result = restTemplate.getForObject(uri, JsonNode.class);
		log.info("Finalizando consulta del item {} resultado {}", itemId, result);
		return result;
	}

	private Optional<Item> findItemCache(String itemId) {

		try {
			log.info("Iniciando consulta del item en cache {}", itemId);
			Item itemCache = itemRepository.findById(itemId);
			if (itemCache != null) {
				log.info("Se encontro item en cache {}", itemCache);
				return Optional.of(itemCache);
			} else {
				log.info("No se encontro item en cache {}", itemId);
				return Optional.empty();
			}
		} catch (Exception e) {
			log.info("No se encontro item en cache {} exception {}", itemId, e);
			return Optional.empty();
		}

	}

	private void saveInCache(Item item) {
		log.info("Guardando item en cache {}", item);
		itemRepository.save(item);
	}

}
