$(document).ready(function() {
    var readURL = function(input) {
        if (input.files && input.files[0]) {
            if(input.files.item(0).size > 524288) {
                $("#error_image_message").text("Размер картинки не должен превышать 512 KB");
                setTimeout(function () {
                    $("#error_image_message").text("");
                }, 2000);
            }else {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('.profile-pic').attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
            }
        }
    }

    $(".file-upload").on('change', function(){
        readURL(this);
    });

    $(".upload-button").on('click', function() {
        $(".file-upload").click();
    });
});