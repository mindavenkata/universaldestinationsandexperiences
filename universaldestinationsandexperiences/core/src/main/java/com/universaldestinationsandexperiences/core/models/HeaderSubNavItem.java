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
 * Child model representing a sub-navigation item in a header dropdown.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderSubNavItem {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String url;

    @ValueMapValue
    private Boolean openInNewTab;

    @ValueMapValue
    private Boolean hasIcon;

    public HeaderSubNavItem() {
    }

    public HeaderSubNavItem(String title, String url) {
        this.title = title;
        this.url = url;
        if (title != null && (title.toUpperCase().contains("SUPPLIER") || title.toUpperCase().contains("MAIL") || title.toUpperCase().contains("BUSINESS"))) {
            this.hasIcon = true;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getFormattedUrl() {
        if (StringUtils.isBlank(url)) {
            return "#";
        }
        if (url.startsWith("/content") && !url.contains(".html") && !url.contains("#")) {
            return url + ".html";
        }
        return url;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public String getTarget() {
        return isOpenInNewTab() ? "_blank" : "_self";
    }

    public boolean isHasIcon() {
        return Boolean.TRUE.equals(hasIcon);
    }
}
