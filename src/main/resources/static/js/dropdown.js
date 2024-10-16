document.addEventListener("DOMContentLoaded", function () {
    const dropdowns = document.querySelectorAll(".dropdown");
  
    dropdowns.forEach((dropdown) => {
      const toggle = dropdown.querySelector(".dropdown-toggle");
      const menu = dropdown.querySelector(".dropdown-menu");
  
      toggle.addEventListener("click", function (event) {
        event.preventDefault();
  
        // Cierra otros menús si están abiertos
        dropdowns.forEach((d) => {
          if (d !== dropdown) {
            d.querySelector(".dropdown-menu").classList.remove("show");
          }
        });
  
        // Alterna la visibilidad del menú actual
        menu.classList.toggle("show");
      });
  
      // Cierra el menú si se hace clic fuera de él
      document.addEventListener("click", function (event) {
        if (
          !dropdown.contains(event.target) &&
          menu.classList.contains("show")
        ) {
          menu.classList.remove("show");
        }
      });
    });
  });
  