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
 * Sling Model for the Universal Destinations &amp; Experiences Universal Footer component.
 */
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UniversalFooterModel {

    public static final String RESOURCE_TYPE = "universaldestinationsandexperiences/components/footer";
    private static final String DEFAULT_COPYRIGHT = "©2026 Universal Studios. All rights reserved.";

    @ValueMapValue
    private String copyrightText;

    @ValueMapValue
    private Boolean showBackToTop;

    @ChildResource(name = "socialLinks")
    private List<FooterSocialLinkItem> socialLinks;

    @ChildResource(name = "legalLinks")
    private List<FooterLegalLinkItem> legalLinks;

    public String getCopyrightText() {
        return StringUtils.isNotBlank(copyrightText) ? copyrightText : DEFAULT_COPYRIGHT;
    }

    public boolean isShowBackToTop() {
        return showBackToTop == null || showBackToTop;
    }

    public List<FooterSocialLinkItem> getSocialLinks() {
        return socialLinks != null ? Collections.unmodifiableList(socialLinks) : Collections.emptyList();
    }

    public List<FooterLegalLinkItem> getLegalLinks() {
        return legalLinks != null ? Collections.unmodifiableList(legalLinks) : Collections.emptyList();
    }

    public boolean hasContent() {
        return true;
    }

    public int getSocialLinkCount() {
        return getSocialLinks().size();
    }

    public int getLegalLinkCount() {
        return getLegalLinks().size();
    }
}
