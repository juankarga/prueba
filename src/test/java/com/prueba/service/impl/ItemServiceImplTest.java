package com.prueba.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.model.Item;
import com.prueba.repository.ItemRepository;
@SpringBootTest
public class ItemServiceImplTest {

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ItemRepository itemRepository;
	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private ItemServiceImpl itemServiceImpl;

	@BeforeEach
	public void setUp() throws JsonMappingException, JsonProcessingException {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtils.setField(itemServiceImpl, "urlMl", "localhost");

		Item item = new Item();
		item.setId("MLCO1");
		item.setPrice(8500f);
		when(itemRepository.findById(any())).thenReturn(item);

		doNothing().when(itemRepository).save(any());

		JsonNode result = objectMapper.readTree("{\"id\":MCO610559808,\"price\":89950.0}");
		when(restTemplate.getForObject(any(URI.class), eq(JsonNode.class))).thenReturn(result);
	}

	@Test
	public void mapItems() {
		Float couponAmount = 7000f;
		List<String> listRequest = new ArrayList();
		listRequest.add("MLCO1");
		listRequest.add("MLCO2");
		listRequest.add("MLCO4");
		Map<String, Float> itemMap = itemServiceImpl.mapItems(listRequest, couponAmount);
		Assert.assertNotNull(itemMap);
	}

}
