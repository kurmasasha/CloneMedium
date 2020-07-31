function showSpinner(container) {
    container.children().hide();
    container.append(
    '<div class="spinner">'+
        '<div class="spinner-border" role="status">'+
            '<span class="sr-only">Loading...</span>'+
        '</div>'+
    '</div>'
    )
}

function hideSpinner(container) {
    container.find('.spinner').remove();
    container.children().show();
}