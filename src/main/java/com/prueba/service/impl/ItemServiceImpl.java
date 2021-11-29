package com.prueba.service.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.prueba.model.Item;
import com.prueba.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<String> calculate(Map<String, Float> items, Float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemMl(String itemId) {

		log.info("Iniciando consulta del item {}", itemId);
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString("https://api.mercadolibre.com/items")
				.path(itemId);

		URI uri = urlBuilder.build().encode().toUri();
		log.info("URL {}", uri.toString());
		JsonNode result = restTemplate.getForObject(uri, JsonNode.class);
		log.info("Finalizando consulta del item {} resultado {}", itemId, result);
		Item itemResult = new Item();
		// id
		itemResult.setId(result.get("id").asText());
		// price
		itemResult.setAmount(result.get("price").asDouble());
		log.info("Resultado info del item {} resultado {}", itemId, itemResult);
		return itemResult;
	}

}
