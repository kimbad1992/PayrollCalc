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

            // 활성화 된 URL이 있는 메뉴 콜랩스 펼치기 및 강조
            $('a[data-target="#' + parentId + '"]').attr('aria-expanded', 'true');
            $this.addClass('font-weight-bold');

            // 최상위 <li> 요소에 active 클래스 추가
            $this.closest('.nav-item').addClass('active');
        }
    });
});

const ComUtils = {
    callFetch : async function (url, data) {
        return new Promise(async (resolve, reject) => {
            const content = document.getElementById('content');
            const loadingLayer = document.createElement('div');
            loadingLayer.id = 'loadingLayer';
            const loadingText = document.createElement('div');
            loadingText.id = 'loadingText';
            loadingText.textContent = 'Loading..';
            loadingLayer.appendChild(loadingText);
            content.appendChild(loadingLayer);

            try {
                const response = await fetch(url, {
                    method: 'POST',
                    body: data
                });

                if (response.ok) {
                    const result = await response.json();
                    console.log('Success:', result);
                    resolve(result);  // 성공 시 결과를 반환
                } else {
                    console.log('Failure:', response.status);
                    resolve(response.status);  // 실패 시 상태 코드를 반환
                }
            } catch (error) {
                console.error('Error:', error);
                reject(error);  // 에러 발생 시 에러 객체를 반환
            } finally {
                content.removeChild(loadingLayer);
            }
        });
    }
}