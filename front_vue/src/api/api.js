import axios from 'axios'

const connect = axios.create({
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