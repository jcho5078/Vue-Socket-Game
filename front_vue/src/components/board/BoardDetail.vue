<template>
  <div class="page_nm">
    <p class="idx">#{{boardNo}}</p>
    <em class="idx">
      작성자: {{boardData.regUserNm}} &nbsp;&nbsp;&nbsp;
    </em>
    <em>
      {{ boardData.regDt[0] + '.' + boardData.regDt[1] + '.'
    + boardData.regDt[2] + ' ' + boardData.regDt[3] + ':' + boardData.regDt[4] }}
    </em>
    <h2 class="title">{{boardData.boardTitle}}</h2>
  </div>
  <div v-html="boardData.boardDetail"></div>
  <button type="button" v-if="checkUser" @click="moveBoardWrite">수정</button>

  <div class="board-comment">
    <ul class="comment">
      <li class="comment-form">
        <form id="commentFrm">
          <h4>댓글쓰기 <span></span></h4>
          <span class="ps_box">
              <input id="comment-input" type="text" placeholder="댓글 내용을 입력해주세요." class="int" name="content" />
            </span>
          <input type="button" @click="writeNewComment" class="btn" value="등록" />
        </form>
      </li>
      <li id="comment-list">
        <ul class="comment-row" v-for="boardComment in boardComments" :key="boardComment.commentNo">
          <li class="comment-id">
            {{boardComment.regUserNm}}
          </li>
          <li class="comment-content">
            {{boardComment.commentDetail}}
          </li>
          <li class="comment-date">
            {{ boardComment.regDt[0] + '.' + boardComment.regDt[1] + '.'
          + boardComment.regDt[2] + ' ' + boardComment.regDt[3] + ':' + boardComment.regDt[4] }}
          </li>
          <li>
            <input type="button" class="comment-delete-btn" value="X"
                   v-if="this.$store.state.userData.userNo == boardComment.regUser" @click="deleteComment(boardComment.commentNo)">
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import {viewBoardDetail, writeBoardComment, deleteBoardComment} from "@/api/backend";
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "BoardDetail",
  data(){
    return {
      boardNo: '',
      regUser: '',
      boardData: {
        boardTitle: '',
        boardDetail: '',
        regDt: '',
        regUserNm: '',
      },
      boardComments: []
    }
  },
  mounted() {
    this.boardNo = this.$route.params.boardNo;
    viewBoardDetail(this.boardNo).then(response => {
      this.boardNo = response.data.boardNo;
      this.regUser = response.data.regUser;
      this.boardData.boardTitle = response.data.boardTitle;
      this.boardData.boardDetail = response.data.boardDetail;
      this.boardData.regDt = response.data.regDt;
      this.boardData.regUserNm = response.data.regUserNm;
      this.boardComments = response.data.boardComments;
    }).catch(error => {
      alert('불러오기 실패:' + error);
    })
  },
  computed: {
    checkUser(){
      let result = false;
      if(!isEmpty(this.$store.state.userData)){
        if(this.regUser === this.$store.state.userData.userNo) result = true;
      }
      return result;
    }
  },
  methods: {
    moveBoardWrite(){
      this.$router.push('/board/write/'+this.boardNo)
    },
    writeNewComment(){
      let commentData = {
        boardNo: this.boardNo,
        regUser: this.regUser,
        commentDetail: document.querySelector('#comment-input').value,
        newComment: true
      }
      writeBoardComment(commentData).then(response => {
        this.boardComments = response.data;
        document.querySelector('#comment-input').value = '';
      }).catch(error => {
        alert('댓글작성 실패:' + error);
      })
    },
    deleteComment(commentNo){
      let commentData = {
        boardNo: this.boardNo,
        commentNo: commentNo,
      }
      deleteBoardComment(commentData).then(response => {
        this.boardComments.forEach(function(comment, idx, obj){
          if(comment.commentNo == commentNo){
            obj.splice(idx, 1);
            return;
          }
        });
      }).catch(error => {
        alert('댓글삭제 실패:' + error);
      })
    }
  }
}
</script>

<style scoped>
ul, li {
  list-style: none;
}
em{
  font-size: 12px;
}
.idx{
  font-weight: bold;
}
.title{
  margin: 20px 0 20px 0;
}
.comment {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  padding: 30px;
  width: 600px;
  margin: 0 auto;
}

.comment > li {
  margin-top: 20px;
}
.comment > li:nth-child(1) {
  margin: 0px;
}

.comment-row {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}

.comment-row {
  margin-top: 20px;
  width: 100%;
}

.comment-row > li:nth-child(2) {
  flex-shrink: 0;
  flex-grow: 1;
  padding-left: 25px;
  z-index: 1;
  width: 100%;
}

.comment-row > li:nth-child(2) {
  width: 85px;
}

.comment-form > form {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

.comment-form > form > h4 {
  width: 100%;
  margin: 14px 0 14px 0;
}

.comment-content {
  word-break: break-all;
  padding-right: 25px;
}

.ps_box {
  display: block;
  position: relative;
  width: 80%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px 10px 14px;
  background: #fff;
  box-sizing: border-box;
}

.ps_box > input {
  outline: none;
}

.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  padding-right: 25px;
  line-height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
  box-sizing: border-box;
  z-index: 10;
}

.btn {
  width: 18%;
  padding: 18px 0 16px;
  text-align: center;
  box-sizing: border-box;
  text-decoration: none;
  border: none;
  background: #333;
  color: #fff;
  font-size: 14px;
}

.comment-delete-btn {
  display: inline-block;
  margin-left: 7px;
  cursor: pointer;
  background-color: #666666;
  color: #dddddd;
  padding: 0 2px 0 2px;
}

.comment-update-input {
  border: none;
  border-bottom: 1px solid #333;
  font-size: 16px;
  color: #666;
  outline: none;
}
</style>