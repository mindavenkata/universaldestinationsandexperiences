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
class ImageCarouselModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ImageCarouselModel.class, ImageCarouselItem.class);
    }

    @Test
    void testEmptyModel() {
        Resource resource = context.create().resource("/content/empty-image-carousel",
            "sling:resourceType", ImageCarouselModel.RESOURCE_TYPE);

        ImageCarouselModel model = resource.adaptTo(ImageCarouselModel.class);
        assertNotNull(model);
        assertFalse(model.hasContent());
        assertEquals(0, model.getImageCount());
        assertFalse(model.isAutoplay());
        assertEquals(5000L, model.getDelay());
        assertEquals("800px", model.getMaxWidth());
        assertTrue(model.getImages().isEmpty());
    }

    @Test
    void testPopulatedModelWithImages() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", ImageCarouselModel.RESOURCE_TYPE);
        props.put("autoplay", true);
        props.put("delay", 6000L);
        props.put("maxWidth", "900px");

        Resource parent = context.create().resource("/content/image-carousel", props);

        Map<String, Object> img1Props = new HashMap<>();
        img1Props.put("image", "/content/dam/universaldestinations/summit.jpg");
        img1Props.put("imageAlt", "Inspiring Future Innovators Summit");
        img1Props.put("caption", "Students gathering at the summit");
        img1Props.put("linkUrl", "/content/universaldestinations/summit");
        img1Props.put("openInNewTab", true);
        context.create().resource("/content/image-carousel/images/item0", img1Props);

        ImageCarouselModel model = parent.adaptTo(ImageCarouselModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals(1, model.getImageCount());
        assertTrue(model.isAutoplay());
        assertEquals(6000L, model.getDelay());
        assertEquals("900px", model.getMaxWidth());

        List<ImageCarouselItem> items = model.getImages();
        assertEquals(1, items.size());

        ImageCarouselItem item = items.get(0);
        assertEquals("/content/dam/universaldestinations/summit.jpg", item.getImage());
        assertEquals("Inspiring Future Innovators Summit", item.getImageAlt());
        assertEquals("Students gathering at the summit", item.getCaption());
        assertEquals("/content/universaldestinations/summit.html", item.getFormattedUrl());
        assertTrue(item.isOpenInNewTab());
        assertEquals("_blank", item.getTarget());
    }
}
