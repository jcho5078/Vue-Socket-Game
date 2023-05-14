import axios from 'axios'

let connect = axios.create({
    headers: {'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': 'PUT, POST, PATCH, DELETE, GET'},
    baseURL: process.env.VUE_APP_API_ENDPOINT
});

/**
 *  로그인
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function login(param){
    return connect.post('/user/login', param);
}

/**
 *  회원가입
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function signUp(param){
    return connect.post('/user/signUp', param);
}

/**
 *  유저데이터 조회
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function getUserInfo(header){
    return connect.get('/user/info', {
        headers: header,
        params: {
            jwtToken: localStorage.getItem("userToken")
        }
    });
}

/**
 * 게시글 조회
 * @param param
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
function viewBoardDetail(param){
    return connect.get('/board/view', param);
}

export {
    login,
    signUp,
    getUserInfo,
    viewBoardDetail
}