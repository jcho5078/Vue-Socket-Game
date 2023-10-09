import axios from 'axios'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

let connect = axios.create({
    headers: {'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'PUT, POST, PATCH, DELETE, GET', 'withCredentials' : true,
        "Authorization": 'Bearer '+localStorage.userToken,
    },
    baseURL: "http://localhost:8080"
});

connect.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    console.log(error);
    if(error.response.data.message.indexOf('JWT expired at') != -1 || error.response.status == 401){
        if (window.confirm("로그인 만료")) {
            location.href = '/';
        }
    }
    return Promise.reject(error);
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

/**
 * 댓글작성
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function writeBoardComment(param){
    return connect.post('/board/comment/write', param);
}

/**
 * 댓글삭제
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
function deleteBoardComment(param){
    return connect.post('/board/comment/delete', param);
}

/**
 * 랭킹 목록
 * @param param
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
function viewRankList(page, size){
    return connect.get('/game/rank', {
        headers: {
            /*'content-type': 'application/json',
            "Authorization": 'Bearer ' + localStorage.getItem("userToken")*/
        },
        params: {
            page: page,
            size: size
        }
    }).catch(error => {
        console.log(error);
    });
}

export {
    login,
    signUp,
    getUserInfo,
    viewBoardList,
    viewBoardDetail,
    writeBoard,
    writeBoardComment,
    deleteBoardComment,
    viewRankList,
}