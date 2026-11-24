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
class FeaturedCarouselModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(FeaturedCarouselModel.class, FeaturedCarouselSlideItem.class);
    }

    @Test
    void testEmptyModel() {
        Resource resource = context.create().resource("/content/empty-carousel",
            "sling:resourceType", FeaturedCarouselModel.RESOURCE_TYPE);

        FeaturedCarouselModel model = resource.adaptTo(FeaturedCarouselModel.class);
        assertNotNull(model);
        assertFalse(model.hasContent());
        assertEquals(0, model.getSlideCount());
        assertTrue(model.isAutoplay());
        assertEquals(5000L, model.getDelay());
        assertEquals("#152e75", model.getBackgroundColor());
        assertTrue(model.getSlides().isEmpty());
    }

    @Test
    void testPopulatedModelWithSlides() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", FeaturedCarouselModel.RESOURCE_TYPE);
        props.put("autoplay", false);
        props.put("delay", 7500L);
        props.put("backgroundColor", "#1b357d");

        Resource parent = context.create().resource("/content/featured-carousel", props);

        Map<String, Object> slide1Props = new HashMap<>();
        slide1Props.put("image", "/content/dam/universaldestinations/horror-unleashed.jpg");
        slide1Props.put("imageAlt", "Universal Horror Unleashed Chicago");
        slide1Props.put("title", "Universal Destinations & Experiences Expands Universal Horror Unleashed Concept with Second Location Planned for Chicago");
        slide1Props.put("ctaText", "Read More");
        slide1Props.put("ctaLink", "/content/universaldestinations/news/chicago");
        slide1Props.put("openInNewTab", true);
        context.create().resource("/content/featured-carousel/slides/item0", slide1Props);

        FeaturedCarouselModel model = parent.adaptTo(FeaturedCarouselModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals(1, model.getSlideCount());
        assertFalse(model.isAutoplay());
        assertEquals(7500L, model.getDelay());
        assertEquals("#1b357d", model.getBackgroundColor());

        List<FeaturedCarouselSlideItem> slides = model.getSlides();
        assertEquals(1, slides.size());

        FeaturedCarouselSlideItem slide = slides.get(0);
        assertEquals("/content/dam/universaldestinations/horror-unleashed.jpg", slide.getImage());
        assertEquals("Universal Horror Unleashed Chicago", slide.getImageAlt());
        assertEquals("Universal Destinations & Experiences Expands Universal Horror Unleashed Concept with Second Location Planned for Chicago", slide.getTitle());
        assertEquals("Read More", slide.getCtaText());
        assertEquals("/content/universaldestinations/news/chicago", slide.getCtaLink());
        assertTrue(slide.isOpenInNewTab());
        assertEquals("_blank", slide.getTarget());
    }
}
