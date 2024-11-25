document.addEventListener("click", function(event) {
    const modal = document.getElementById("successModal");
    const modalContent = document.getElementById("successModalContent");
    if (event.target.classList.contains("close") || event.target.id === "closeModal") {
        modal.style.animationName = "fadeOut";
        modalContent.style.animationName = "slideOut";
        setTimeout(() => {
            modal.style.display = "none";
        }, 290);
    }
});