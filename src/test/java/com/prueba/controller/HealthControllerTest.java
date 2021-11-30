package com.prueba.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HealthControllerTest {

	@InjectMocks
	private HealthController healthController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void healthTest() throws Exception {
		String health = healthController.health();
		Assert.assertEquals("OK", health);
	}

}
