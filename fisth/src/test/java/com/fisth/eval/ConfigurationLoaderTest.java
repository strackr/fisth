/*
 * This file is part of fisth, an FSM-based Texas Hold'em hand evaluator.
 * Copyright (C) 2010 Robert Strack <robert.strack@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.fisth.eval;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fisth.eval.ConfigurationLoadFailureException;
import com.fisth.eval.ConfigurationLoader;

public class ConfigurationLoaderTest {

	@DataProvider(name = "existing-ser-files")
	public Object[][] existingSerFilesProvider() {
		return new Object[][] {
				{ short[][].class, "/rank_states.ser" },
				{ short[][].class, "/suit_states.ser" },
				{ short[][].class, "/suit_transitions.ser" }
		};
	}

	@Test(dataProvider = "existing-ser-files")
	public void shouldReadSerFiles(Class<?> objectClass, String resourceName) {
		ConfigurationLoader reader = new ConfigurationLoader();

		Object obj = reader.loadObjectFromResource(resourceName);
		
		assertNotNull(obj);
		assertEquals(obj.getClass(), objectClass);
	}
	
	@Test(expectedExceptions = ConfigurationLoadFailureException.class)
	public void shouldThrowExeptionWhenResourceDoesNotExist() {
		ConfigurationLoader reader = new ConfigurationLoader();

		reader.loadObjectFromResource("non-existent-resource");
	}

}
