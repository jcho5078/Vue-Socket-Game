<template>
  <form @submit.prevent="generateBoard">
    <div>
      제목: <input type="text" name="boardTitle" v-model="boardData.boardTitle">
    </div>
    <div>
      내용: <textarea v-model="boardData.boardDetail"/>
    </div>
  </form>
  <button v-if="isModify" type="button" @click="generateBoard()">수정</button>
  <button v-else type="button" @click="generateBoard()">저장</button>
</template>

<script>
import {viewBoardDetail, writeBoard} from '@/api/backend';
import {isEmpty} from "@/common/commonUtil";

export default {
  name: "BoardWrite",
  created() {
    /**
     * 글 수정
     */
    if(this.isModify){
      this.getBoardInfo(this.$route.params.boardNo);
    }
  },
  computed: {
    isModify(){
      return !isEmpty(this.$route.params.boardNo);
    }
  },
  data(){
    return {
      boardData: {
        boardNo: this.$route.params.boardNo,
        boardTitle: '',
        boardDetail: '',
        userNo: localStorage.getItem('userData').userNo
      }
    }
  },
  methods: {
    generateBoard(){
      writeBoard(this.boardData).then(response => {
        this.viewBoard(response.data.boardNo);
      }).catch(error => {
        console.log('게시물 작성 실패 : ' + error);
      });
    },
    getBoardInfo(boardNo){
      viewBoardDetail(boardNo).then(response => {
        this.boardData.boardNo = response.data.boardNo;
        this.boardData.boardTitle = response.data.boardTitle;
        this.boardData.boardDetail = response.data.boardDetail;
      }).catch(error => {
        alert('불러오기 실패:' + error);
      })
    },
    viewBoard(boardNo){
      this.$router.push('/board/view/' + boardNo);
    }
  }
}
</script>

<style scoped>
div{
  margin: 20px 0px;
}
input[type=text]{
  width: 450px;
}
textarea{
  width: 450px;
  resize: none;
}
</style>