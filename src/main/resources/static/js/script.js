// Asegúrate de que Swiper está inicializado en este archivo
document.addEventListener('DOMContentLoaded', function() {
    var swiper = new Swiper('.mySwiper-1', {
      loop: true,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
    });
  });