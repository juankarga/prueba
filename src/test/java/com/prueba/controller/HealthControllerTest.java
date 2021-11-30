package com.prueba.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HealthControllerTest {

	@InjectMocks
	private HealthController healthController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void healthTest() throws Exception {
		String health = healthController.health();
		Assert.assertEquals("OK", health);
	}

}
