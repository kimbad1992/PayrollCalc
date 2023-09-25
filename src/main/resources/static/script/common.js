// common.js
$(document).ready(function() {
    $('.menu-toggle').click(function() {
        const parent = $(this).parent();
        parent.toggleClass('show');

        const arrow = $(this).find('.arrow');
        if (parent.hasClass('show')) {
            arrow.removeClass('arrow-down').addClass('arrow-up');
        } else {
            arrow.removeClass('arrow-up').addClass('arrow-down');
        }
    });
});
