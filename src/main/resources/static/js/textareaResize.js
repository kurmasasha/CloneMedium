jQuery(document).ready(function(){
    jQuery("#textareaResize").on("keydown keyup", function(){
        this.style.height = "1px";
        this.style.height = (this.scrollHeight) + "px";
    });
});
