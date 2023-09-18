<template>
  <div>
    #{{boardNo}}
    <h3>{{boardData.boardTitle}}</h3>
    <p>작성자: {{boardData.userNm}}</p>
  </div>
  <div>{{boardData.boardDetail}}</div>
  <button type="button" v-if="checkUser" @click="moveBoardWrite">수정</button>
</template>

<script>
import {viewBoardDetail} from "@/api/backend";
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
      }
    }
  },
  mounted() {
    this.boardNo = this.$route.params.boardNo;
    viewBoardDetail(this.boardNo).then(response => {
      console.log(response.data);
      this.boardNo = response.data.boardNo;
      this.regUser = response.data.regUser;
      this.boardData.boardTitle = response.data.boardTitle;
      this.boardData.boardDetail = response.data.boardDetail;
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
    }
  }
}
</script>

<style scoped>

</style>