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
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the Universal Destinations &amp; Experiences Universal Header component.
 */
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UniversalHeaderModel {

    public static final String RESOURCE_TYPE = "universaldestinationsandexperiences/components/header";
    private static final String DEFAULT_BRAND_TITLE = "Corporate";
    private static final String DEFAULT_BRAND_SUBTITLE = "Communications";
    private static final String DEFAULT_HOME_LINK = "/content/universaldestinationsandexperiences/us/en";
    private static final String DEFAULT_BG_COLOR = "#0066d6";

    @ValueMapValue
    private String logoImage;

    @ValueMapValue
    private String brandTitle;

    @ValueMapValue
    private String brandSubtitle;

    @ValueMapValue
    private String homeLink;

    @ValueMapValue
    private String backgroundColor;

    @ChildResource(name = "navItems")
    private List<HeaderNavItem> navItems;

    public String getLogoImage() {
        return logoImage;
    }

    public String getBrandTitle() {
        return StringUtils.isNotBlank(brandTitle) ? brandTitle : DEFAULT_BRAND_TITLE;
    }

    public String getBrandSubtitle() {
        return StringUtils.isNotBlank(brandSubtitle) ? brandSubtitle : DEFAULT_BRAND_SUBTITLE;
    }

    public String getHomeLink() {
        return StringUtils.isNotBlank(homeLink) ? homeLink : DEFAULT_HOME_LINK;
    }

    public String getBackgroundColor() {
        return StringUtils.isNotBlank(backgroundColor) ? backgroundColor : DEFAULT_BG_COLOR;
    }

    public List<HeaderNavItem> getNavItems() {
        if (navItems != null && !navItems.isEmpty()) {
            return Collections.unmodifiableList(navItems);
        }

        // Default navigation items matching Universal Destinations & Experiences mockup
        List<HeaderNavItem> defaults = new ArrayList<>();
        defaults.add(new HeaderNavItem("ABOUT", "#", "MISSION|#\nLEADERSHIP|#\nHISTORY|#\nSUPPLIERS & BUSINESS PARTNERS|#"));
        defaults.add(new HeaderNavItem("FEATURES", "#", null));
        defaults.add(new HeaderNavItem("IMPACT", "#", "COMMUNITY|#\nSUSTAINABILITY|#"));
        defaults.add(new HeaderNavItem("CAREERS", "#", "EXPLORE JOBS|#\nLIFE AT UNIVERSAL|#"));
        defaults.add(new HeaderNavItem("NEWSROOM", "#", "PRESS RELEASES|#\nMEDIA ASSETS|#"));
        defaults.add(new HeaderNavItem("UDX", "#", "PARKS & RESORTS|#\nENTERTAINMENT|#"));
        return Collections.unmodifiableList(defaults);
    }

    public boolean hasContent() {
        return true;
    }

    public int getNavItemCount() {
        return getNavItems().size();
    }
}
