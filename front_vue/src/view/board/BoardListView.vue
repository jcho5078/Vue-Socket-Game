<template>
    <div>
      <h3>게시물</h3>
      <board-list/>
      {{this.$store.state.counter}}
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