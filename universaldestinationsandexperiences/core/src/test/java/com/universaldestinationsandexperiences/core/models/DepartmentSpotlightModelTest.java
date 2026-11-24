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
class DepartmentSpotlightModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(DepartmentSpotlightModel.class, DepartmentRoleItem.class);
    }

    @Test
    void testEmptyModelDefaults() {
        Resource resource = context.create().resource("/content/empty-spotlight",
            "sling:resourceType", DepartmentSpotlightModel.RESOURCE_TYPE);

        DepartmentSpotlightModel model = resource.adaptTo(DepartmentSpotlightModel.class);
        assertNotNull(model);
        assertEquals(DepartmentSpotlightModel.LAYOUT_IMAGE_LEFT, model.getLayout());
        assertEquals(DepartmentSpotlightModel.THEME_WHITE, model.getTheme());
        assertEquals(DepartmentSpotlightModel.CTA_STYLE_LINK, model.getCtaStyle());
        assertEquals("cmp-department-spotlight--image-left cmp-department-spotlight--theme-white", model.getModifierClasses());
        assertNull(model.getEyebrow());
        assertNull(model.getTitle());
        assertNull(model.getDescription());
        assertNull(model.getImage());
        assertEquals("Universal Destinations & Experiences Department", model.getImageAlt());
        assertNull(model.getBadgeText());
        assertFalse(model.hasPrimaryCta());
        assertEquals("", model.getPrimaryCtaLink());
        assertEquals("_self", model.getPrimaryCtaTarget());
        assertFalse(model.hasSecondaryCta());
        assertEquals("", model.getSecondaryCtaLink());
        assertEquals("_self", model.getSecondaryCtaTarget());
        assertFalse(model.hasRoles());
        assertEquals(0, model.getRoleCount());
        assertTrue(model.getRoles().isEmpty());
        assertFalse(model.hasContent());
    }

    @Test
    void testPopulatedModelImageLeftWithRolesAndCtas() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", DepartmentSpotlightModel.RESOURCE_TYPE);
        props.put("layout", "image-left");
        props.put("theme", "theme-light-gray");
        props.put("ctaStyle", "button");
        props.put("eyebrow", "CAREER SPOTLIGHT");
        props.put("title", "Technical Services");
        props.put("description", "Use your technical and mechanical skills to keep our resort in perfect working order.");
        props.put("image", "/content/dam/universaldestinations/tech-services.jpg");
        props.put("imageAlt", "Technician in blue hard hat");
        props.put("primaryCtaLabel", "Explore Open Roles");
        props.put("primaryCtaLink", "/content/universaldestinations/careers/technical-services");
        props.put("primaryCtaOpenInNewTab", false);
        props.put("secondaryCtaLabel", "Meet the Team");
        props.put("secondaryCtaLink", "https://corporate.universaldestinationsandexperiences.com/team");
        props.put("secondaryCtaOpenInNewTab", true);

        Resource parent = context.create().resource("/content/tech-spotlight", props);

        Map<String, Object> role1Props = new HashMap<>();
        role1Props.put("roleTitle", "Ride & Show Mechanics");
        role1Props.put("roleTag", "High Demand");
        role1Props.put("roleDescription", "Maintain advanced theme park ride systems.");
        role1Props.put("roleLink", "/content/universaldestinations/careers/mechanics");
        role1Props.put("openInNewTab", true);
        context.create().resource("/content/tech-spotlight/roles/item0", role1Props);

        DepartmentSpotlightModel model = parent.adaptTo(DepartmentSpotlightModel.class);
        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("image-left", model.getLayout());
        assertEquals("theme-light-gray", model.getTheme());
        assertEquals("button", model.getCtaStyle());
        assertEquals("cmp-department-spotlight--image-left cmp-department-spotlight--theme-light-gray", model.getModifierClasses());
        assertEquals("CAREER SPOTLIGHT", model.getEyebrow());
        assertEquals("Technical Services", model.getTitle());
        assertEquals("Use your technical and mechanical skills to keep our resort in perfect working order.", model.getDescription());
        assertEquals("/content/dam/universaldestinations/tech-services.jpg", model.getImage());
        assertEquals("Technician in blue hard hat", model.getImageAlt());

        assertTrue(model.hasPrimaryCta());
        assertEquals("Explore Open Roles", model.getPrimaryCtaLabel());
        assertEquals("/content/universaldestinations/careers/technical-services.html", model.getPrimaryCtaLink());
        assertFalse(model.isPrimaryCtaOpenInNewTab());
        assertEquals("_self", model.getPrimaryCtaTarget());

        assertTrue(model.hasSecondaryCta());
        assertEquals("Meet the Team", model.getSecondaryCtaLabel());
        assertEquals("https://corporate.universaldestinationsandexperiences.com/team", model.getSecondaryCtaLink());
        assertTrue(model.isSecondaryCtaOpenInNewTab());
        assertEquals("_blank", model.getSecondaryCtaTarget());

        assertTrue(model.hasRoles());
        assertEquals(1, model.getRoleCount());
    }

    @Test
    void testImageRightAndSoftBlueVariation() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", DepartmentSpotlightModel.RESOURCE_TYPE);
        props.put("layout", "image-right");
        props.put("theme", "theme-soft-blue");
        props.put("title", "Culinary");
        props.put("description", "Join our Culinary family and learn from our very own masters of cuisine!");
        props.put("image", "/content/dam/culinary.jpg");
        props.put("primaryCtaLabel", "Explore Open Roles");
        props.put("primaryCtaLink", "/content/universaldestinations/careers/culinary");

        Resource res = context.create().resource("/content/culinary-spotlight", props);
        DepartmentSpotlightModel model = res.adaptTo(DepartmentSpotlightModel.class);

        assertNotNull(model);
        assertEquals("image-right", model.getLayout());
        assertEquals("theme-soft-blue", model.getTheme());
        assertEquals("link", model.getCtaStyle());
        assertEquals("cmp-department-spotlight--image-right cmp-department-spotlight--theme-soft-blue", model.getModifierClasses());
        assertEquals("Culinary", model.getTitle());
        assertEquals("Culinary", model.getImageAlt());
        assertEquals("/content/universaldestinations/careers/culinary.html", model.getPrimaryCtaLink());
    }

    @Test
    void testBannerLayoutAndNavyVariation() {
        Map<String, Object> props = new HashMap<>();
        props.put("sling:resourceType", DepartmentSpotlightModel.RESOURCE_TYPE);
        props.put("layout", "banner");
        props.put("theme", "theme-navy");
        props.put("title", "Universal Epic Universe");

        Resource res = context.create().resource("/content/epic-universe-spotlight", props);
        DepartmentSpotlightModel model = res.adaptTo(DepartmentSpotlightModel.class);

        assertNotNull(model);
        assertEquals("banner", model.getLayout());
        assertEquals("theme-navy", model.getTheme());
        assertEquals("cmp-department-spotlight--banner cmp-department-spotlight--theme-navy", model.getModifierClasses());
    }
}
