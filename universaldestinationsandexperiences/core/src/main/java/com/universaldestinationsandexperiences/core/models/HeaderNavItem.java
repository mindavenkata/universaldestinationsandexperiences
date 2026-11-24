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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Child model representing a main navigation item (with optional dropdown) in the Universal Header.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderNavItem {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String link;

    @ValueMapValue
    private Boolean openInNewTab;

    @ValueMapValue
    private String dropdownItemsText;

    @ChildResource(name = "subItems")
    private List<HeaderSubNavItem> childSubItems;

    public HeaderNavItem() {
    }

    public HeaderNavItem(String title, String link, String dropdownItemsText) {
        this.title = title;
        this.link = link;
        this.dropdownItemsText = dropdownItemsText;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getFormattedUrl() {
        if (StringUtils.isBlank(link)) {
            return "#";
        }
        if (link.startsWith("/content") && !link.contains(".html") && !link.contains("#")) {
            return link + ".html";
        }
        return link;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public String getTarget() {
        return isOpenInNewTab() ? "_blank" : "_self";
    }

    public List<HeaderSubNavItem> getSubItems() {
        if (StringUtils.isNotBlank(dropdownItemsText)) {
            List<HeaderSubNavItem> parsed = new ArrayList<>();
            String[] lines = dropdownItemsText.split("\\r?\\n");
            for (String line : lines) {
                if (StringUtils.isNotBlank(line)) {
                    String[] parts = line.split("\\|", 2);
                    String itemTitle = parts[0].trim();
                    String itemUrl = (parts.length > 1) ? parts[1].trim() : "#";
                    parsed.add(new HeaderSubNavItem(itemTitle, itemUrl));
                }
            }
            return Collections.unmodifiableList(parsed);
        }

        if (childSubItems != null && !childSubItems.isEmpty()) {
            return Collections.unmodifiableList(childSubItems);
        }

        return Collections.emptyList();
    }

    public boolean isHasDropdown() {
        return !getSubItems().isEmpty();
    }
}
