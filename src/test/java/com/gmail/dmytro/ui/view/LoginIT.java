package com.gmail.dmytro.ui.view;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.gmail.dmytro.AbstractIT;

public class LoginIT extends AbstractIT {

	@Test
	public void logoutWorks() {
		loginAsBarista();
		$(MenuElement.class).first().logout();
		Assert.assertEquals("Email", findElement(By.id("login-label")).getText());
	}

}
