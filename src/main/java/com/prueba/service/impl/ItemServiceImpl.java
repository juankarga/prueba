package com.prueba.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.prueba.exception.NotFoundException;
import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;
import com.prueba.service.IItemService;
import com.prueba.utils.JacksonUtils;

@Service
public class ItemServiceImpl implements IItemService {

	private static final String FIELD_PRICE = "price";

	private static final String FIELD_ID = "id";

	private Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Value("${url.meli}")
	private String urlMl;

	private RestTemplate restTemplate;

	private ItemRepository itemRepository;

	public ItemServiceImpl(RestTemplate restTemplate, ItemRepository itemRepository) {
		super();
		this.restTemplate = restTemplate;
		this.itemRepository = itemRepository;
	}

	@Override
	public Map<String, Float> mapItems(List<String> itemIds, Float couponAmount) {
		log.info("Iniciando busqueda y mappeo de items {}", JacksonUtils.getPlainJson(itemIds));
		Map<String, Float> itemMap = new HashMap<>();
		for (String itemId : itemIds) {
			try {
				Item item = findItemMl(itemId);
				// si el monto del item es mayor al monto del cupon no vale la pena agregarlo
				if (couponAmount.compareTo(item.getPrice()) > 0) {
					itemMap.put(item.getId(), item.getPrice());
				}
			} catch (NotFoundException e) {
				log.error("No se encontro el item indicado: {}", itemId);
			}

		}
		log.info("Finaliza busqueda y mappeo de items {}", JacksonUtils.getPlainJson(itemMap));
		return itemMap;
	}

	private Item findItemMl(String itemId) throws NotFoundException {
		try {
			log.info("Iniciando consulta del item {}", itemId);

			Optional<Item> itemOptional = findItemCache(itemId);
			if (itemOptional.isEmpty()) {
				JsonNode result = invokeApiMl(itemId);
				Item itemResult = mapItemResult(result);
				log.info("Resultado info del item {} resultado {}", itemId, JacksonUtils.getPlainJson(itemResult));
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
		itemResult.setId(result.get(FIELD_ID).asText());
		itemResult.setPrice(Float.valueOf(result.get(FIELD_PRICE).asText()));
		return itemResult;
	}

	private JsonNode invokeApiMl(String itemId) {
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(urlMl).pathSegment(itemId);
		URI uri = urlBuilder.build().encode().toUri();
		log.info("URL {}", uri);
		JsonNode result = restTemplate.getForObject(uri, JsonNode.class);
		log.info("Finalizando consulta del item {} resultado {}", itemId, result);
		return result;
	}

	private Optional<Item> findItemCache(String itemId) {

		try {
			log.info("Iniciando consulta del item en cache {}", itemId);
			Item itemCache = itemRepository.findById(itemId);
			if (itemCache != null) {
				log.info("Se encontro item en cache {}", JacksonUtils.getPlainJson(itemCache));
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
		log.info("Guardando item en cache {}", JacksonUtils.getPlainJson(item));
		itemRepository.save(item);
	}

}
