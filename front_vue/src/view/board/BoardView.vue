<template>
  BoardView
  <div>{{boardNo}}</div>
</template>

<script>
export default {
  name: "BoardView",
  data(){
    return {
      boardNo: '',
      boardTitle: '',
      userNo: '',
      userNm: '',
      boardDt: ''
    }
  },
  mounted() {
    this.boardNo = this.$route.params.boardNo;
  }
}
</script>

<style scoped>

</style>