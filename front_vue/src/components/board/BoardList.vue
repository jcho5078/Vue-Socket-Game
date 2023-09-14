<template>
  <table class="boardTable">
    <thead>
    <tr>
      <th style="width: 5%;">No</th>
      <th style="width: 70%;">제목</th>
      <th style="width: 15%;">작성자</th>
      <th style="width: 10%;">작성일자</th>
    </tr>
    </thead>
    <tbody>
    <tr v-if="checkBoardIsEmpty">
      <td colspan="4">작성글이 없습니다.</td>
    </tr>
    <tr v-else v-for="board in boardList"
        :key="board.boardNo"
        @click="viewBoard($event, board.boardNo)">
      <td>{{ board.boardNo }}</td>
      <td>{{ board.boardTitle }}</td>
      <td>{{ board.regUserNm }}</td>
      <td>{{ board.regDt[0] + '.' + board.regDt[1] + '.' + board.regDt[2] + ' ' + board.regDt[3] + ':' + board.regDt[4] }}</td>
    </tr>
    </tbody>
  </table>
  <button type="button" v-if="checkLogin" @click="moveBoardWrite">글작성</button>
</template>

<script>
import {isEmpty} from "@/common/commonUtil";
import {viewBoardList} from "@/api/backend";

export default {
  name: "BoardList",
  data() {
    return {
      total: 0,
      boardList: [

      ],
    }
  },
  mounted() {
    // 시작 게시물
    this.getBoardList(0, 5);
  },
  computed: {
    checkLogin(){
      return isEmpty(localStorage.userToken) == false;
    },
    checkBoardIsEmpty(){
      if(this.boardList) return this.boardList.length < 1;
      else return true;
    }
  },
  methods: {
    getBoardList(page, size){
      viewBoardList(page, size).then(response => {
        this.total = response.data.total;
        this.boardList = response.data;
      }).catch(error => {
        alert('Error : ' + error);
      });
    },
    viewBoard(e, boardNo){
      this.$router.push('/board/view/' + boardNo);
    },
    moveBoardWrite(){
      this.$router.push('/board/write')
    },
  }
}
</script>

<style scoped>
.boardTable {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.boardTable td, .boardTable th {
  border: 1px solid #ddd;
  padding: 8px;
}

.boardTable tr:nth-child(even){background-color: #f2f2f2;}

.boardTable tr:hover {background-color: #ddd;}

.boardTable th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: center;
  background-color: #04AA6D;
  color: white;
}

.boardTable td {
  text-align: center;
}
</style>