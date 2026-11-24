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

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.universaldestinationsandexperiences.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class UniversalFooterModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(
            UniversalFooterModel.class,
            FooterSocialLinkItem.class,
            FooterLegalLinkItem.class
        );
    }

    @Test
    void testEmptyFooterDefaults() {
        Resource resource = context.create().resource("/content/footer-default",
            "sling:resourceType", UniversalFooterModel.RESOURCE_TYPE);

        UniversalFooterModel model = resource.adaptTo(UniversalFooterModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("©2026 Universal Studios. All rights reserved.", model.getCopyrightText());
        assertTrue(model.isShowBackToTop());
        assertTrue(model.getSocialLinks().isEmpty());
        assertTrue(model.getLegalLinks().isEmpty());
    }

    @Test
    void testPopulatedFooter() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", UniversalFooterModel.RESOURCE_TYPE);
        props.put("copyrightText", "©2026 Universal Studios. Custom rights.");
        props.put("showBackToTop", false);

        Resource parent = context.create().resource("/content/footer-populated", props);

        // Social Link 1: YouTube
        Map<String, Object> ytProps = new HashMap<>();
        ytProps.put("platform", "youtube");
        ytProps.put("label", "Find us on Youtube");
        ytProps.put("linkUrl", "https://youtube.com/@Universal");
        ytProps.put("openInNewTab", true);
        context.create().resource("/content/footer-populated/socialLinks/item0", ytProps);

        // Social Link 2: LinkedIn
        Map<String, Object> liProps = new HashMap<>();
        liProps.put("platform", "linkedin");
        liProps.put("label", "Find us on LinkedIn");
        liProps.put("linkUrl", "https://linkedin.com/company/universal");
        liProps.put("openInNewTab", true);
        context.create().resource("/content/footer-populated/socialLinks/item1", liProps);

        // Legal Link 1: Privacy Center
        Map<String, Object> legal1 = new HashMap<>();
        legal1.put("linkText", "Privacy Center");
        legal1.put("linkUrl", "/content/universaldestinations/privacy-center");
        context.create().resource("/content/footer-populated/legalLinks/item0", legal1);

        UniversalFooterModel model = parent.adaptTo(UniversalFooterModel.class);
        assertNotNull(model);
        assertEquals("©2026 Universal Studios. Custom rights.", model.getCopyrightText());
        assertFalse(model.isShowBackToTop());
        assertEquals(2, model.getSocialLinkCount());
        assertEquals(1, model.getLegalLinkCount());

        List<FooterSocialLinkItem> socialList = model.getSocialLinks();
        assertEquals("youtube", socialList.get(0).getPlatform());
        assertTrue(socialList.get(0).isYoutube());
        assertEquals("Find us on Youtube", socialList.get(0).getLabel());
        assertEquals("_blank", socialList.get(0).getTarget());

        assertEquals("linkedin", socialList.get(1).getPlatform());
        assertTrue(socialList.get(1).isLinkedin());

        List<FooterLegalLinkItem> legalList = model.getLegalLinks();
        assertEquals("Privacy Center", legalList.get(0).getLinkText());
        assertEquals("/content/universaldestinations/privacy-center.html", legalList.get(0).getFormattedUrl());
    }
}
