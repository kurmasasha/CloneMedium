let imageEditForm = document.getElementById("imageEditForm");
let resetButtonEditForm = document.getElementById("resetButtonEditForm");

resetButtonEditForm.addEventListener('click', hideImage, false)

function hideImage() {
    imageEditForm.style.display = "none";
}