
function EmployeeList() {
    return (
        <div className="container-fluid">
            <h1 className="h3 mb-2 text-gray-800">사원 조회</h1>
            <p className="mb-4">
                검색 창에 검색어 입력 시 해당 검색어로 필터링된 항목이 조회됩니다.<br/>
                <span className="text-primary">이름</span>을 클릭 시 해당 사원의 정보 수정 페이지로 이동합니다.
            </p>
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <h6 className="m-0 font-weight-bold text-primary">사원 목록</h6>
                </div>
                <div className="card-body">
                    <div className="table-responsive">
                        <table className="table table-bordered" id="dataTable" width="100%" cellSpacing="0">
                            <thead>
                            <tr>
                                <th>순서</th>
                                <th>소속명</th>
                                <th>성 명</th>
                                <th>근무지</th>
                                <th>직급</th>
                                <th>급여</th>
                                <th>OT시급</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td></td>
                                <td></td> 
                                <td>
                                    <a href="" className="text-primary"></a>
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeeList;