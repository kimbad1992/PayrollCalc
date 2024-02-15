import React, {useState} from 'react';
import { Link } from 'react-router-dom';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        // 여기에 로그인 로직 구현, 예: fetch API 또는 Axios를 사용한 서버 요청
        console.log('로그인 시도')
        console.log(username)
        console.log(password)
        try {
            const response = await axios.post('/api/userLogin', {
                username,
                password,
            });
            // 로그인 요청 성공
            console.log(response.data); // 응답 데이터 처리
            // 로그인 성공 후 리다이렉션, 예를 들어 메인 페이지로
            navigate('/main');
        } catch (error) {
            console.error('로그인 요청 실패:', error);
            // 에러 처리, 예를 들어 사용자에게 에러 메시지 표시
        }
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-xl-10 col-lg-12 col-md-9">
                    <div className="card o-hidden border-0 shadow-lg my-5">
                        <div className="card-body p-0">
                            <div className="row">
                                <div className="col-lg-6 d-none d-lg-block jy-login-image"></div>
                                <div className="col-lg-6">
                                    <div className="p-5">
                                        <div className="text-center">
                                            <h1 className="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                        </div>
                                        <form className="user" onSubmit={handleSubmit}>
                                            <div className="form-group">
                                                <input type="text" className="form-control form-control-user"
                                                       value={username} onChange={e => setUsername(e.target.value)}
                                                       placeholder="관리자 ID"/>
                                            </div>
                                            <div className="form-group">
                                                <input type="password" value={password}
                                                       className="form-control form-control-user"
                                                       onChange={e => setPassword(e.target.value)}
                                                       placeholder="비밀번호"/>
                                                <span className="text-danger"></span> {/* 에러 메시지 출력 위치 */}
                                            </div>
                                            <input type="submit" className="btn btn-primary btn-user btn-block"
                                                   value="Login"/>
                                            <a href="https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=[YOUR_REDIRECT_URI]&prompt=consent&response_type=code&client_id=[YOUR_CLIENT_ID]&scope=email%20profile%20openid&access_type=offline"
                                               className="btn btn-google btn-user btn-block">
                                                <i className="fab fa-google fa-fw"></i> Login with Google
                                            </a>
                                        </form>
                                        <hr/>
                                        <div className="text-center">
                                            <Link to="/forgot-password" className="small">비밀번호 찾기</Link>
                                        </div>
                                        <div className="text-center">
                                            <Link to="/signup" className="small">계정 생성</Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;
