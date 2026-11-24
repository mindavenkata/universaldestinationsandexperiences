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

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Slide item model for the Featured Carousel multifield.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedCarouselSlideItem {

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String imageAlt;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private Boolean openInNewTab;

    public String getImage() {
        return image;
    }

    public String getImageAlt() {
        if (StringUtils.isNotBlank(imageAlt)) {
            return imageAlt;
        }
        return StringUtils.isNotBlank(title) ? title : "Universal Destinations Slide Image";
    }

    public String getTitle() {
        return title;
    }

    public String getCtaText() {
        return StringUtils.isNotBlank(ctaText) ? ctaText : "Read More";
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public String getTarget() {
        return isOpenInNewTab() ? "_blank" : "_self";
    }
}
