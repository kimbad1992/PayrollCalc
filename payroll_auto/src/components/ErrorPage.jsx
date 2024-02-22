import {Link} from "react-router-dom";

function ErrorPage() {
    return(
        <div className="container-fluid">
            <div className="text-center">
                <div className="error mx-auto" data-text='404'>404</div>
                <p className="lead text-gray-800 mb-5">존재하지 않는 경로입니다.</p>
                <p className="text-gray-500 mb-0">에러가 발생했습니다.</p>
                <Link to='/'>⬅️메인 페이지로</Link>
            </div>

        </div>
    )
}

export default ErrorPage;