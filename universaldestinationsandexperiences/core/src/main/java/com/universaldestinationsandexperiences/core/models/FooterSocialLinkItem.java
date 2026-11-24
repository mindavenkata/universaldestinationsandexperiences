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
 * Child model representing a social media channel in the Universal Footer.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterSocialLinkItem {

    @ValueMapValue
    private String platform;

    @ValueMapValue
    private String label;

    @ValueMapValue
    private String linkUrl;

    @ValueMapValue
    private Boolean openInNewTab;

    public String getPlatform() {
        return StringUtils.isNotBlank(platform) ? platform.toLowerCase() : "custom";
    }

    public String getLabel() {
        if (StringUtils.isNotBlank(label)) {
            return label;
        }
        if ("youtube".equalsIgnoreCase(platform)) {
            return "Find us on Youtube";
        } else if ("linkedin".equalsIgnoreCase(platform)) {
            return "Find us on LinkedIn";
        }
        return "Find us online";
    }

    public String getLinkUrl() {
        return StringUtils.isNotBlank(linkUrl) ? linkUrl : "#";
    }

    public boolean isOpenInNewTab() {
        return openInNewTab == null || openInNewTab;
    }

    public String getTarget() {
        return isOpenInNewTab() ? "_blank" : "_self";
    }

    public boolean isYoutube() {
        return "youtube".equalsIgnoreCase(getPlatform());
    }

    public boolean isLinkedin() {
        return "linkedin".equalsIgnoreCase(getPlatform());
    }
}
