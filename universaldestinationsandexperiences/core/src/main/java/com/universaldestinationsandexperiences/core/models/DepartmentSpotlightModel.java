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
 * Sling Model for the Universal Destinations &amp; Experiences Featured Department Spotlight component.
 * Supports multiple layout variations (image-left, image-right, banner) and theme variations (white, soft-blue, light-gray, navy).
 */
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DepartmentSpotlightModel {

    public static final String RESOURCE_TYPE = "universaldestinationsandexperiences/components/department-spotlight";

    public static final String LAYOUT_IMAGE_LEFT = "image-left";
    public static final String LAYOUT_IMAGE_RIGHT = "image-right";
    public static final String LAYOUT_BANNER = "banner";

    public static final String THEME_WHITE = "theme-white";
    public static final String THEME_SOFT_BLUE = "theme-soft-blue";
    public static final String THEME_LIGHT_GRAY = "theme-light-gray";
    public static final String THEME_NAVY = "theme-navy";

    public static final String CTA_STYLE_LINK = "link";
    public static final String CTA_STYLE_BUTTON = "button";

    @ValueMapValue
    private String layout;

    @ValueMapValue
    private String theme;

    @ValueMapValue
    private String ctaStyle;

    @ValueMapValue
    private String eyebrow;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String imageAlt;

    @ValueMapValue
    private String badgeText;

    @ValueMapValue
    private String primaryCtaLabel;

    @ValueMapValue
    private String primaryCtaLink;

    @ValueMapValue
    private Boolean primaryCtaOpenInNewTab;

    @ValueMapValue
    private String secondaryCtaLabel;

    @ValueMapValue
    private String secondaryCtaLink;

    @ValueMapValue
    private Boolean secondaryCtaOpenInNewTab;

    @ChildResource(name = "roles")
    private List<DepartmentRoleItem> roles;

    public String getLayout() {
        if (LAYOUT_IMAGE_RIGHT.equalsIgnoreCase(layout)) {
            return LAYOUT_IMAGE_RIGHT;
        } else if (LAYOUT_BANNER.equalsIgnoreCase(layout)) {
            return LAYOUT_BANNER;
        }
        return LAYOUT_IMAGE_LEFT;
    }

    public String getTheme() {
        if ("theme-blue-tint".equalsIgnoreCase(theme) || THEME_SOFT_BLUE.equalsIgnoreCase(theme)) {
            return THEME_SOFT_BLUE;
        } else if (THEME_LIGHT_GRAY.equalsIgnoreCase(theme)) {
            return THEME_LIGHT_GRAY;
        } else if (THEME_NAVY.equalsIgnoreCase(theme)) {
            return THEME_NAVY;
        }
        return THEME_WHITE;
    }

    public String getCtaStyle() {
        if (CTA_STYLE_BUTTON.equalsIgnoreCase(ctaStyle)) {
            return CTA_STYLE_BUTTON;
        }
        return CTA_STYLE_LINK;
    }

    public String getEyebrow() {
        return eyebrow;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getImageAlt() {
        if (StringUtils.isNotBlank(imageAlt)) {
            return imageAlt;
        }
        return StringUtils.isNotBlank(title) ? title : "Universal Destinations & Experiences Department";
    }

    public String getBadgeText() {
        return badgeText;
    }

    public String getPrimaryCtaLabel() {
        return primaryCtaLabel;
    }

    public String getPrimaryCtaLink() {
        return formatLink(primaryCtaLink);
    }

    public boolean isPrimaryCtaOpenInNewTab() {
        return Boolean.TRUE.equals(primaryCtaOpenInNewTab);
    }

    public String getPrimaryCtaTarget() {
        return isPrimaryCtaOpenInNewTab() ? "_blank" : "_self";
    }

    public boolean hasPrimaryCta() {
        return StringUtils.isNotBlank(primaryCtaLabel) && StringUtils.isNotBlank(primaryCtaLink);
    }

    public String getSecondaryCtaLabel() {
        return secondaryCtaLabel;
    }

    public String getSecondaryCtaLink() {
        return formatLink(secondaryCtaLink);
    }

    public boolean isSecondaryCtaOpenInNewTab() {
        return Boolean.TRUE.equals(secondaryCtaOpenInNewTab);
    }

    public String getSecondaryCtaTarget() {
        return isSecondaryCtaOpenInNewTab() ? "_blank" : "_self";
    }

    public boolean hasSecondaryCta() {
        return StringUtils.isNotBlank(secondaryCtaLabel) && StringUtils.isNotBlank(secondaryCtaLink);
    }

    public List<DepartmentRoleItem> getRoles() {
        return roles != null ? Collections.unmodifiableList(roles) : Collections.emptyList();
    }

    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
    }

    public int getRoleCount() {
        return getRoles().size();
    }

    public String getModifierClasses() {
        return "cmp-department-spotlight--" + getLayout() + " cmp-department-spotlight--" + getTheme();
    }

    public boolean hasContent() {
        return StringUtils.isNotBlank(title) || StringUtils.isNotBlank(image) || StringUtils.isNotBlank(description);
    }

    private String formatLink(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (url.startsWith("/content") && !url.contains(".html") && !url.contains("#") && !url.contains("?")) {
            return url + ".html";
        }
        return url;
    }
}
