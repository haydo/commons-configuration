/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration.builder.combined;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.ConfigurationBuilder;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Test class for {@code CombinedBuilderParameters}.
 *
 * @version $Id$
 */
public class TestCombinedBuilderParameters
{
    /**
     * Tests fromParameters() if the map does not contain an instance.
     */
    @Test
    public void testFromParametersNotFound()
    {
        assertNull("Got an instance",
                CombinedBuilderParameters
                        .fromParameters(new HashMap<String, Object>()));
    }

    /**
     * Tests whether a new instance can be created if none is found in the
     * parameters map.
     */
    @Test
    public void testFromParametersCreate()
    {
        CombinedBuilderParameters params =
                CombinedBuilderParameters.fromParameters(
                        new HashMap<String, Object>(), true);
        assertNotNull("No instance", params);
        assertNull("Got data", params.getDefinitionBuilder());
    }

    /**
     * Tests whether an instance can be obtained from a parameters map.
     */
    @Test
    public void testFromParametersExisting()
    {
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        Map<String, Object> map = params.getParameters();
        assertSame("Wrong result", params,
                CombinedBuilderParameters.fromParameters(map));
    }

    /**
     * Tests whether the definition builder can be set.
     */
    @Test
    public void testSetDefinitionBuilder()
    {
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        assertNull("Got a definition builder", params.getDefinitionBuilder());
        ConfigurationBuilder<XMLConfiguration> builder =
                new BasicConfigurationBuilder<XMLConfiguration>(
                        XMLConfiguration.class);
        assertSame("Wrong result", params, params.setDefinitionBuilder(builder));
        assertSame("Builder was not set", builder,
                params.getDefinitionBuilder());
    }

    /**
     * Tests whether the map with providers is initially empty.
     */
    @Test
    public void testGetProvidersInitial()
    {
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        assertTrue("Got providers", params.getProviders().isEmpty());
    }

    /**
     * Tests whether a new builder provider can be registered.
     */
    @Test
    public void testRegisterProvider()
    {
        ConfigurationBuilderProvider provider =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        EasyMock.replay(provider);
        String tagName = "testTag";
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        assertSame("Wrong result", params,
                params.registerProvider(tagName, provider));
        Map<String, ConfigurationBuilderProvider> providers =
                params.getProviders();
        assertEquals("Wrong number of providers", 1, providers.size());
        assertSame("Wrong provider (1)", provider, providers.get(tagName));
        assertSame("Wrong provider (2)", provider,
                params.providerForTag(tagName));
    }

    /**
     * Tries to register a provider without a tag name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterProviderNoTag()
    {
        new CombinedBuilderParameters().registerProvider(null,
                EasyMock.createMock(ConfigurationBuilderProvider.class));
    }

    /**
     * Tries to register a null provider.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterProviderNoProvider()
    {
        new CombinedBuilderParameters().registerProvider("aTag", null);
    }

    /**
     * Tests that the map with providers cannot be modified.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetProvidersModify()
    {
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        params.getProviders().put("tag",
                EasyMock.createMock(ConfigurationBuilderProvider.class));
    }

    /**
     * Tests whether missing providers can be registered.
     */
    @Test
    public void testRegisterMissingProviders()
    {
        ConfigurationBuilderProvider provider1 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider2 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider3 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        String tagPrefix = "testTag";
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        params.registerProvider(tagPrefix, provider1);
        Map<String, ConfigurationBuilderProvider> map =
                new HashMap<String, ConfigurationBuilderProvider>();
        map.put(tagPrefix, provider2);
        map.put(tagPrefix + 1, provider3);
        assertSame("Wrong result", params, params.registerMissingProviders(map));
        assertEquals("Wrong number of providers", 2, params.getProviders()
                .size());
        assertSame("Wrong provider (1)", provider1,
                params.providerForTag(tagPrefix));
        assertSame("Wrong provider (2)", provider3,
                params.providerForTag(tagPrefix + 1));
    }

    /**
     * Tries to register a null map with missing providers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMissingProvidersNullMap()
    {
        new CombinedBuilderParameters().registerMissingProviders(null);
    }

    /**
     * Tries to register a map with missing providers containing a null entry.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMissingProvidersNullEntry()
    {
        Map<String, ConfigurationBuilderProvider> map =
                new HashMap<String, ConfigurationBuilderProvider>();
        map.put("tag", null);
        new CombinedBuilderParameters().registerMissingProviders(map);
    }

    /**
     * Tests the result for an unknown provider.
     */
    @Test
    public void testProviderForUnknown()
    {
        CombinedBuilderParameters params = new CombinedBuilderParameters();
        assertNull("Got a provider", params.providerForTag("someTag"));
    }
}
