// Universal Destinations & Experiences - Header Component JS
(function () {
    "use strict";

    function initHeader(el) {
        var toggle = el.querySelector('[data-cmp-hook-header="toggle"]');
        var dropdownToggles = el.querySelectorAll('[data-cmp-hook-header="dropdownToggle"]');

        if (toggle) {
            toggle.addEventListener("click", function () {
                var isOpen = el.classList.toggle("cmp-universal-header--open");
                toggle.setAttribute("aria-expanded", isOpen ? "true" : "false");
            });
        }

        dropdownToggles.forEach(function (btn) {
            btn.addEventListener("click", function () {
                var item = btn.closest('[data-cmp-hook-header="item"]');
                if (item) {
                    var isExpanded = item.classList.toggle("cmp-universal-header__item--open");
                    btn.setAttribute("aria-expanded", isExpanded ? "true" : "false");
                }
            });
        });

        document.addEventListener("click", function (e) {
            if (!el.contains(e.target)) {
                var openItems = el.querySelectorAll(".cmp-universal-header__item--open");
                openItems.forEach(function (openItem) {
                    openItem.classList.remove("cmp-universal-header__item--open");
                    var btn = openItem.querySelector('[data-cmp-hook-header="dropdownToggle"]');
                    if (btn) {
                        btn.setAttribute("aria-expanded", "false");
                    }
                });
            }
        });
    }

    function initAll() {
        var headers = document.querySelectorAll('[data-cmp-is="universalHeader"]');
        headers.forEach(function (header) {
            if (!header.dataset.initialized) {
                header.dataset.initialized = "true";
                initHeader(header);
            }
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initAll);
    } else {
        initAll();
    }
})();
