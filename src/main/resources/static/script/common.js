$(() => {
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

    // 에러 메시지 삭제용
    $('input').on('input', () => {
        // 테두리 제거
        if ($(this).hasClass('is-invalid')) {
            $(this).removeClass('is-invalid');
        }

        let parentForm = $(this).closest('.form-group');
        if (parentForm.length > 0) {
            parentForm[0].dataset.errorAdded = 'false'; // 에러 메시지 추가 상태를 False로 변경
            parentForm.find('span.error-text').remove(); // 에러 메시지 span 요소 삭제
        }
    });

    // 세션 잔여시간 표기
    function formatTime(milliseconds) {
        let totalSeconds = Math.floor(milliseconds / 1000);
        let minutes = Math.floor(totalSeconds / 60);
        let seconds = totalSeconds % 60;

        // 분과 초를 두 자리수로 포맷팅
        minutes = String(minutes).padStart(2, '0');
        seconds = String(seconds).padStart(2, '0');

        return minutes + ':' + seconds;
    }

    function startTimer() {
        const timerInterval = setInterval(() => {
            remainingTime -= 1000; // 1초씩 감소
            if (remainingTime <= 0) {
                clearInterval(timerInterval); // 타이머 종료
                document.getElementById('sessionTime').textContent = '세션 만료';
                // 추가적인 로직 (예: 로그아웃 페이지로 리다이렉트)
            } else {
                document.getElementById('sessionTime').textContent = formatTime(remainingTime);
            }
        }, 1000);
    }

    startTimer();
});

const ComUtils = {
    callFetch : async (url, data, options) => {
        return new Promise(async (resolve, reject) => {
            // CSRF Token
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
            // options가 null이면 빈 객체를 사용
            if (options == null) {
                options = {};
            }

            // fetch 메서드에서 사용할 공통 로딩 레이어
            const content = document.getElementById('content');
            const loadingLayer = document.createElement('div');
            loadingLayer.id = 'loadingLayer';
            const loadingText = document.createElement('div');
            loadingText.id = 'loadingText';
            loadingText.textContent = 'Loading..';
            loadingLayer.appendChild(loadingText);
            content.appendChild(loadingLayer);

            const defaultOptions = {
                method: 'POST',
                body: data,
                headers: {
                    [csrfHeader]: csrfToken
                }
            };

            // defaultOptions과 전달받은 options을 병합
            const fetchOptions = {
                ...defaultOptions,
                ...options,
                headers: {
                    ...defaultOptions.headers,
                    ...(options.headers || {}) // options.headers가 undefined 또는 null일 경우 빈 객체를 사용
                }
            };

            try {
                const response = await fetch(url, fetchOptions);

                if (response.ok) {
                    const result = await response.json();
                    resolve(result);
                } else {
                    const errorResponse = await response.json();
                    reject(errorResponse);
                }

            } catch (error) {
                reject(error);  // 에러 발생 시 에러 객체를 반환
            } finally {
                content.removeChild(loadingLayer);
            }
        });
    }
}