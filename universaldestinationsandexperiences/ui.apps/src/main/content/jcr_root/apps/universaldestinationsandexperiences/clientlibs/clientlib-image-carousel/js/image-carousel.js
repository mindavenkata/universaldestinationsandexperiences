(function () {
    "use strict";

    function initImageCarousel(el) {
        var slides = el.querySelectorAll('[data-cmp-hook-carousel="slide"]');
        var indicators = el.querySelectorAll('[data-cmp-hook-carousel="indicator"]');
        var prevBtn = el.querySelector('[data-cmp-hook-carousel="previous"]');
        var nextBtn = el.querySelector('[data-cmp-hook-carousel="next"]');
        var isAutoplay = el.getAttribute("data-cmp-autoplay") === "true";
        var delay = parseInt(el.getAttribute("data-cmp-delay"), 10) || 5000;

        if (slides.length <= 1) {
            if (prevBtn) prevBtn.style.display = "none";
            if (nextBtn) nextBtn.style.display = "none";
            var pagination = el.querySelector(".cmp-image-carousel__pagination");
            if (pagination) pagination.style.display = "none";
            return;
        }

        var currentIndex = 0;
        var timer = null;

        function showSlide(index) {
            if (index < 0) {
                index = slides.length - 1;
            } else if (index >= slides.length) {
                index = 0;
            }

            slides.forEach(function (slide, i) {
                if (i === index) {
                    slide.classList.add("cmp-image-carousel__slide--active");
                    slide.setAttribute("aria-hidden", "false");
                } else {
                    slide.classList.remove("cmp-image-carousel__slide--active");
                    slide.setAttribute("aria-hidden", "true");
                }
            });

            indicators.forEach(function (dot, i) {
                if (i === index) {
                    dot.classList.add("cmp-image-carousel__dot--active");
                    dot.setAttribute("aria-selected", "true");
                } else {
                    dot.classList.remove("cmp-image-carousel__dot--active");
                    dot.setAttribute("aria-selected", "false");
                }
            });

            currentIndex = index;
        }

        function next() {
            showSlide(currentIndex + 1);
        }

        function prev() {
            showSlide(currentIndex - 1);
        }

        function startAutoplay() {
            if (isAutoplay && !timer) {
                timer = setInterval(next, delay);
            }
        }

        function stopAutoplay() {
            if (timer) {
                clearInterval(timer);
                timer = null;
            }
        }

        if (prevBtn) {
            prevBtn.addEventListener("click", function () {
                prev();
                stopAutoplay();
                startAutoplay();
            });
        }

        if (nextBtn) {
            nextBtn.addEventListener("click", function () {
                next();
                stopAutoplay();
                startAutoplay();
            });
        }

        indicators.forEach(function (dot) {
            dot.addEventListener("click", function () {
                var target = parseInt(dot.getAttribute("data-slide-target"), 10);
                if (!isNaN(target)) {
                    showSlide(target);
                    stopAutoplay();
                    startAutoplay();
                }
            });
        });

        // Touch Swipe
        var touchStartX = 0;
        var touchEndX = 0;

        el.addEventListener("touchstart", function (e) {
            touchStartX = e.changedTouches[0].screenX;
            stopAutoplay();
        }, { passive: true });

        el.addEventListener("touchend", function (e) {
            touchEndX = e.changedTouches[0].screenX;
            if (touchStartX - touchEndX > 50) {
                next();
            } else if (touchEndX - touchStartX > 50) {
                prev();
            }
            startAutoplay();
        }, { passive: true });

        // Hover pause
        el.addEventListener("mouseenter", stopAutoplay);
        el.addEventListener("mouseleave", startAutoplay);

        // Keyboard
        el.addEventListener("keydown", function (e) {
            if (e.key === "ArrowLeft") {
                prev();
            } else if (e.key === "ArrowRight") {
                next();
            }
        });

        showSlide(0);
        startAutoplay();
    }

    function initAll() {
        var carousels = document.querySelectorAll('[data-cmp-is="imageCarousel"]');
        carousels.forEach(function (carousel) {
            if (!carousel.dataset.initialized) {
                carousel.dataset.initialized = "true";
                initImageCarousel(carousel);
            }
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initAll);
    } else {
        initAll();
    }
})();
