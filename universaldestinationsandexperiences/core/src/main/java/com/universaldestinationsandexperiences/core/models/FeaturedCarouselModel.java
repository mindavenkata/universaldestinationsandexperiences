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

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the Universal Destinations &amp; Experiences Featured Carousel component.
 */
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FeaturedCarouselModel {

    public static final String RESOURCE_TYPE = "universaldestinationsandexperiences/components/featured-carousel";
    private static final String DEFAULT_BG_COLOR = "#152e75";
    private static final long DEFAULT_DELAY = 5000L;

    @ValueMapValue
    private Boolean autoplay;

    @ValueMapValue
    private Long delay;

    @ValueMapValue
    private String backgroundColor;

    @ChildResource(name = "slides")
    private List<FeaturedCarouselSlideItem> slides;

    public boolean isAutoplay() {
        return autoplay == null || autoplay;
    }

    public long getDelay() {
        return (delay != null && delay > 0) ? delay : DEFAULT_DELAY;
    }

    public String getBackgroundColor() {
        return StringUtils.isNotBlank(backgroundColor) ? backgroundColor : DEFAULT_BG_COLOR;
    }

    public List<FeaturedCarouselSlideItem> getSlides() {
        return slides != null ? Collections.unmodifiableList(slides) : Collections.emptyList();
    }

    public boolean hasContent() {
        return slides != null && !slides.isEmpty();
    }

    public int getSlideCount() {
        return getSlides().size();
    }
}
