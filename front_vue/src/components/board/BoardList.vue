<template>
  <h3>게시판</h3>
  <screen>
    <table class="boardTable">
      <thead>
      <tr>
        <th>No</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일자</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="board in boardList"
          :key="board.boardNo"
          @click="viewBoard($event, board.boardNo)">
        <td>{{ board.boardNo }}</td>
        <td>{{ board.boardTitle }}</td>
        <td>{{ board.userNm }}</td>
        <td>{{ board.boardDt }}</td>
      </tr>
      <tr v-if="boardList === null">
        작성글이 없습니다.
      </tr>
      </tbody>
    </table>
  </screen>
</template>

<script>

//import {viewBoardDetail} from "@/api/api";

export default {
  name: "BoardList",
  methods: {
    viewBoard(e, boardNo){
      this.$router.push('/board/view/' + boardNo);
    }
  },
  data() {
    return {
      boardList:[
        {
          boardNo: 1,
          boardTitle: '글제목1',
          userNo: 1,
          userNm: '글쓴이1',
          boardDt: '20230508'
        },
        {
          boardNo: 2,
          boardTitle: '글제목2',
          userNo: 1,
          userNm: '글쓴이1',
          boardDt: '20230508'
        },{
          boardNo: 3,
          boardTitle: '글3',
          userNo: 2,
          userNm: '글쓴이2',
          boardDt: '20230508'
        },
      ]
    }
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
  text-align: left;
}
</style>