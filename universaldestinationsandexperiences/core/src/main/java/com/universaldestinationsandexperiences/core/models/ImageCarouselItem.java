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
 * Child model representing a single image item in the Image Carousel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageCarouselItem {

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String imageAlt;

    @ValueMapValue
    private String caption;

    @ValueMapValue
    private String linkUrl;

    @ValueMapValue
    private Boolean openInNewTab;

    public String getImage() {
        return image;
    }

    public String getImageAlt() {
        if (StringUtils.isNotBlank(imageAlt)) {
            return imageAlt;
        }
        return StringUtils.isNotBlank(caption) ? caption : "Universal Destinations Image";
    }

    public String getCaption() {
        return caption;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getFormattedUrl() {
        if (StringUtils.isBlank(linkUrl)) {
            return "#";
        }
        if (linkUrl.startsWith("/content") && !linkUrl.contains(".html") && !linkUrl.contains("#")) {
            return linkUrl + ".html";
        }
        return linkUrl;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public String getTarget() {
        return isOpenInNewTab() ? "_blank" : "_self";
    }
}
