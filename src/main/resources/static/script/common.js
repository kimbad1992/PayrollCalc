$(document).ready(function() {


    // 현재 URL 가져오기
    var currentUrl = window.location.pathname;

    // 모든 서브메뉴 아이템을 순회
    $('.collapse-item').each(function() {
    var $this = $(this);

    // 서브메뉴의 URL과 현재 URL이 일치하는지 확인
    var subMenuUrl = $this.attr('href');
    if (subMenuUrl === currentUrl) {
        // 부모 collapse의 ID 가져오기
        var parentId = $this.data('parent-id');

        // 부모 collapse 활성화
        $('#' + parentId).addClass('show');

        // aria-expanded 속성 설정
        $('a[data-target="#' + parentId + '"]').attr('aria-expanded', 'true');

        // 글씨 굵게
        $this.addClass('font-weight-bold');

        // 최상위 <li> 요소에 active 클래스 추가
        $this.closest('.nav-item').addClass('active');
    }
    });
});