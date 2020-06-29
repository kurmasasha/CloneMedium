$(document).ready(function () {
    const maxCount = 2000;

    $("#counter").html(maxCount);
    $("#textareaResize").keyup(function () {
        let revText = this.value.length;

        if (this.value.length > maxCount) {
            this.value = this.value.substr(0, maxCount);
        }
        let cnt = (maxCount - revText);
        if (cnt <= 0) {
            $("#counter").html('0');
        } else {
            $("#counter").html(cnt);
        }
    });
});