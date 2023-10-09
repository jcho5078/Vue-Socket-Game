<template>
    <div class="page_nm">
      <h3>게시물</h3>
      <board-list/>
    </div>
</template>

<script>
import BoardList from "@/components/board/BoardList";
export default {
  name: "BoardListView",
  components: {BoardList}
}
</script>

<style scoped>

</style>