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
 * Sling Model for the Universal Destinations &amp; Experiences News Card Grid component.
 */
@Model(
    adaptables = {Resource.class, SlingHttpServletRequest.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class NewsCardGridModel {

    public static final String RESOURCE_TYPE = "universaldestinationsandexperiences/components/news-card-grid";
    private static final String DEFAULT_SECTION_TITLE = "See What's Happening In Our Universe";

    @ValueMapValue
    private String title;

    @ChildResource(name = "cards")
    private List<NewsCardItem> cards;

    public String getTitle() {
        return StringUtils.isNotBlank(title) ? title : DEFAULT_SECTION_TITLE;
    }

    public List<NewsCardItem> getCards() {
        return cards != null ? Collections.unmodifiableList(cards) : Collections.emptyList();
    }

    public boolean hasContent() {
        return (cards != null && !cards.isEmpty()) || StringUtils.isNotBlank(title);
    }

    public int getCardCount() {
        return getCards().size();
    }
}
