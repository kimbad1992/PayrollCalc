    import React, {useEffect, useState} from 'react';
    import {Link, useNavigate} from 'react-router-dom';
    import {useDispatch, useSelector} from "react-redux";
    import { requestLogin } from "../reducers/authSlice";

    function LoginPage() {
        const [username, setUsername] = useState('');
        const [password, setPassword] = useState('');
        const dispatch = useDispatch();
        const navigate = useNavigate();
        const errMsg = useSelector(state => state.auth.errorMsg);

        const handleSubmit = async (e) => {
            e.preventDefault();
            dispatch(requestLogin({ username, password }))
                .unwrap()
                .then(()=> {
                    navigate('/dashboard')
                })
                .catch((error) => {
                    console.log("로그인 에러", error);
                })
        };

        useEffect(() => {
            // 컴포넌트가 마운트될 때 <body>에 클래스 추가
            document.body.classList.add("bg-gradient-primary");

            // 컴포넌트가 언마운트될 때 <body>에서 해당 클래스 제거
            return () => {
                document.body.classList.remove("bg-gradient-primary");
            };
        }, []); // 빈 배열을 의존성 배열로 제공하여 컴포넌트 마운트 시에만 실행되도록 함

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
                                                    <span className="text-danger error-message">{errMsg}</span>
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
