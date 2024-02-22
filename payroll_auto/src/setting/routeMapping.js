// 미리 각 페이지 컴포넌트를 import 합니다.
import EmployeeList from '../components/employee/EmployeeList';
// import EmployeeRegister from './components/EmployeeRegister';
// import EmployeeExcel from './components/EmployeeExcel';
// import CommonCode from './components/CommonCode';
// import Administrator from './components/Administrator';
// import MenuConfig from './components/MenuConfig';
// import KakaoMap from './components/KakaoMap';
// import Email from './components/Email';
// import RealtimeChat from './components/RealtimeChat';
// import LostArk from './components/LostArk';
// import LLM from './components/LLM';

// 경로와 컴포넌트를 매핑하는 객체
const routeMappings = [
    { path: "/employee/list", component: EmployeeList },
    // { path: "/employee/register", component: EmployeeRegister },
    // { path: "/employee/excel", component: EmployeeExcel },
    // { path: "/system/commonCode", component: CommonCode },
    // { path: "/system/administration", component: Administrator },
    // { path: "/system/menu", component: MenuConfig },
    // { path: "/playground/map", component: KakaoMap },
    // { path: "/playground/mail", component: Email },
    // { path: "/playground/chat", component: RealtimeChat },
    // { path: "/playground/lostark", component: LostArk },
    // { path: "/playground/llm", component: LLM },
    // 필요한 추가 경로와 컴포넌트 매핑
];

export default routeMappings;
