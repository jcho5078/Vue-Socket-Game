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
 * 게시글 조회
 * @param param
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
function viewBoardDetail(param){
    return connect.get('/board/view', param);
}

export {
    login,
    viewBoardDetail
}