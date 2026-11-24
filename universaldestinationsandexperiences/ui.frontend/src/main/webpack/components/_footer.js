// Universal Destinations & Experiences - Footer Component JS
(function () {
    "use strict";

    function initFooter(el) {
        var backToTopBtn = el.querySelector('[data-cmp-hook-footer="backToTop"]');
        if (backToTopBtn) {
            backToTopBtn.addEventListener("click", function () {
                window.scrollTo({
                    top: 0,
                    behavior: "smooth"
                });
            });
        }
    }

    function initAll() {
        var footers = document.querySelectorAll('[data-cmp-is="universalFooter"]');
        footers.forEach(function (footer) {
            if (!footer.dataset.initialized) {
                footer.dataset.initialized = "true";
                initFooter(footer);
            }
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initAll);
    } else {
        initAll();
    }
})();
