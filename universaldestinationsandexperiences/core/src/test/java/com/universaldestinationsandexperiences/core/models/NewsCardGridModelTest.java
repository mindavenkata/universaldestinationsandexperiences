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
class NewsCardGridModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(NewsCardGridModel.class, NewsCardItem.class);
    }

    @Test
    void testEmptyModel() {
        Resource resource = context.create().resource("/content/empty-news-grid",
            "sling:resourceType", NewsCardGridModel.RESOURCE_TYPE);

        NewsCardGridModel model = resource.adaptTo(NewsCardGridModel.class);
        assertNotNull(model);
        assertEquals("See What's Happening In Our Universe", model.getTitle());
        assertEquals(0, model.getCardCount());
        assertTrue(model.getCards().isEmpty());
    }

    @Test
    void testPopulatedModelWithCards() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", NewsCardGridModel.RESOURCE_TYPE);
        props.put("title", "Latest News from Universal");

        Resource parent = context.create().resource("/content/news-grid", props);

        Map<String, Object> cardProps = new HashMap<>();
        cardProps.put("image", "/content/dam/universaldestinations/holiday-merch.jpg");
        cardProps.put("imageAlt", "Holiday Merchandise");
        cardProps.put("cardTitle", "Universal Unwraps All-New Holiday Merchandise Collections");
        cardProps.put("description", "Guests Can Celebrate the Season with Festive Merchandise...");
        cardProps.put("pagePath", "/content/universaldestinations/en/news/holiday-collection");
        cardProps.put("linkText", "Read More...");
        cardProps.put("openInNewTab", false);
        context.create().resource("/content/news-grid/cards/item0", cardProps);

        NewsCardGridModel model = parent.adaptTo(NewsCardGridModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("Latest News from Universal", model.getTitle());
        assertEquals(1, model.getCardCount());

        List<NewsCardItem> cards = model.getCards();
        assertEquals(1, cards.size());

        NewsCardItem card = cards.get(0);
        assertEquals("/content/dam/universaldestinations/holiday-merch.jpg", card.getImage());
        assertEquals("Holiday Merchandise", card.getImageAlt());
        assertEquals("Universal Unwraps All-New Holiday Merchandise Collections", card.getCardTitle());
        assertEquals("Guests Can Celebrate the Season with Festive Merchandise...", card.getDescription());
        assertEquals("/content/universaldestinations/en/news/holiday-collection", card.getPagePath());
        assertEquals("/content/universaldestinations/en/news/holiday-collection", card.getLinkUrl());
        assertEquals("Read More...", card.getLinkText());
        assertFalse(card.isOpenInNewTab());
        assertEquals("_self", card.getTarget());
    }
}
