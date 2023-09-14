import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

let connect = axios.create({
    headers: {'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'PUT, POST, PATCH, DELETE, GET', 'withCredentials' : true,
        "Authorization": 'Bearer '+localStorage.userToken,
    },
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
function getUserInfo(){
    return connect.get('/user/info', {
        headers: {
            'content-type': 'application/json',
            'Authorization': 'Bearer '+localStorage.getItem("userToken"),
        },
        params: {

        }
    });
}

/**
 * 게시글 목록
 * @param param
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
function viewBoardList(page, size){
    return connect.get('/board/list', {
        headers: {
            /*'content-type': 'application/json',
            "Authorization": 'Bearer ' + localStorage.getItem("userToken")*/
        },
        params: {
            page: page,
            size: size
        }
    });
}

/**
 * 게시글 조회
 * @param param
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
function viewBoardDetail(boardNo){
    return connect.get('/board/'+boardNo, {
        headers: {

        }
    });
}

/**
 * 게시글 작성
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function writeBoard(param){
    return connect.post('/board/write', param);
}

export {
    login,
    signUp,
    getUserInfo,
    viewBoardList,
    viewBoardDetail,
    writeBoard,
}