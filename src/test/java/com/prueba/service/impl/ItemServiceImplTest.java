package com.prueba.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;

class ItemServiceImplTest {

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemServiceImpl itemServiceImpl;

	@BeforeEach
	public void setUp() throws JsonMappingException, JsonProcessingException {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtils.setField(itemServiceImpl, "urlMl", "localhost");

		doNothing().when(itemRepository).save(any());

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.readTree("{\"id\":\"MLCO2\",\"price\":8950.0}");
		when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(result);
	}

	@Test
	void mapItemsWithCache() {

		Item item = new Item();
		item.setId("MLCO1");
		item.setPrice(8500f);
		when(itemRepository.findById(any())).thenReturn(item);

		Float couponAmount = 10000f;
		List<String> listRequest = new ArrayList();
		listRequest.add("MLCO1");
		listRequest.add("MLCO2");
		listRequest.add("MLCO4");
		Map<String, Float> itemMap = itemServiceImpl.mapItems(listRequest, couponAmount);
		Assert.assertNotNull(itemMap);
		Assert.assertEquals(1, itemMap.size());
		Assert.assertEquals(Float.valueOf(8500f), itemMap.get("MLCO1"));
	}

	@Test
	public void mapItemsWithOutCache() {

		when(itemRepository.findById(any())).thenReturn(null);

		Float couponAmount = 10000f;
		List<String> listRequest = new ArrayList();
		listRequest.add("MLCO1");
		listRequest.add("MLCO2");
		listRequest.add("MLCO4");
		Map<String, Float> itemMap = itemServiceImpl.mapItems(listRequest, couponAmount);
		Assert.assertNotNull(itemMap);
		Assert.assertEquals(1, itemMap.size());
		Assert.assertEquals(Float.valueOf(8950f), itemMap.get("MLCO2"));
	}

}
