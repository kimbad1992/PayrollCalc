import axios from 'axios';

axios.interceptors.request.use(requestConfig => {
    // 요청 인터셉터 로직
    console.log("엑쉬오스 요청")
    return requestConfig;
}, error => {
    // 요청 에러 처리 로직
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    // 응답 인터셉터 로직
    console.log("엑쉬오스 응답")
    return response;
}, error => {
    // 응답 에러 처리 로직
    return Promise.reject(error);
});
