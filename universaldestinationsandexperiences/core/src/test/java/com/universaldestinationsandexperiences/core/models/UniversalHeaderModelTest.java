/*
 *  Copyright 2026 Universal Destinations and Experiences
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.universaldestinationsandexperiences.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.universaldestinationsandexperiences.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class UniversalHeaderModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(
            UniversalHeaderModel.class,
            HeaderNavItem.class,
            HeaderSubNavItem.class
        );
    }

    @Test
    void testEmptyHeaderDefaults() {
        Resource resource = context.create().resource("/content/header-default",
            "sling:resourceType", UniversalHeaderModel.RESOURCE_TYPE);

        UniversalHeaderModel model = resource.adaptTo(UniversalHeaderModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("Corporate", model.getBrandTitle());
        assertEquals("Communications", model.getBrandSubtitle());
        assertEquals("/content/universaldestinationsandexperiences/us/en", model.getHomeLink());
        assertEquals("#0066d6", model.getBackgroundColor());

        // Assert default nav items
        List<HeaderNavItem> navList = model.getNavItems();
        assertEquals(6, navList.size());
        assertEquals("ABOUT", navList.get(0).getTitle());
        assertTrue(navList.get(0).isHasDropdown());
        assertEquals(4, navList.get(0).getSubItems().size());
        assertEquals("MISSION", navList.get(0).getSubItems().get(0).getTitle());
    }

    @Test
    void testPopulatedHeader() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", UniversalHeaderModel.RESOURCE_TYPE);
        props.put("brandTitle", "Global");
        props.put("brandSubtitle", "News");
        props.put("homeLink", "/content/universaldestinations/home");
        props.put("backgroundColor", "#0055b8");

        Resource parent = context.create().resource("/content/header-populated", props);

        Map<String, Object> itemProps = new HashMap<>();
        itemProps.put("title", "ABOUT");
        itemProps.put("link", "/content/universaldestinations/about");
        itemProps.put("dropdownItemsText", "MISSION|/content/mission\nLEADERSHIP|/content/leadership\nSUPPLIERS & BUSINESS PARTNERS|/content/suppliers");
        context.create().resource("/content/header-populated/navItems/item0", itemProps);

        UniversalHeaderModel model = parent.adaptTo(UniversalHeaderModel.class);
        assertNotNull(model);
        assertEquals("Global", model.getBrandTitle());
        assertEquals("News", model.getBrandSubtitle());
        assertEquals("/content/universaldestinations/home", model.getHomeLink());
        assertEquals("#0055b8", model.getBackgroundColor());

        List<HeaderNavItem> items = model.getNavItems();
        assertEquals(1, items.size());

        HeaderNavItem aboutItem = items.get(0);
        assertEquals("ABOUT", aboutItem.getTitle());
        assertEquals("/content/universaldestinations/about.html", aboutItem.getFormattedUrl());
        assertTrue(aboutItem.isHasDropdown());

        List<HeaderSubNavItem> subList = aboutItem.getSubItems();
        assertEquals(3, subList.size());
        assertEquals("MISSION", subList.get(0).getTitle());
        assertEquals("/content/mission.html", subList.get(0).getFormattedUrl());
        assertTrue(subList.get(2).isHasIcon());
    }
}
