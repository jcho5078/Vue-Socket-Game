<template>
  <div class="rank_list">
    <table class="rankTable">
      <thead>
      <tr>
        <th style="width: 5%;"><img src="/tropi.png" width="30">순위</th>
        <th style="width: 70%;">유저</th>
        <th style="width: 15%;">kill</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="rank in rankList"
          :key="rank.rank">
        <td>
          <img v-if="rank.rank == 1" src="/1st-place.png" width="20">
          <img v-else-if="rank.rank == 2" src="/2st-place.png" width="20">
          <img v-else-if="rank.rank == 3" src="/3st-place.png" width="20">
          {{ rank.rank }}
        </td>
        <td>{{ rank.userNm }}</td>
        <td>{{ rank.killCnt }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {viewRankList} from "@/api/backend";

export default {
  name: "RankList",
  data() {
    return {
      total: 0,
      rankList: [

      ],
    }
  },
  mounted() {
    // 시작 게시물
    this.getRankList(0, 10);
  },
  computed: {

  },
  methods: {
    getRankList(page, size){
      viewRankList(page, size).then(response => {
        this.total = response.data.total;
        this.rankList = response.data;
      }).catch(error => {
        alert('Error : ' + error);
      });
    },
    viewRankDetail(e, userNo){
      this.$router.push('/rank/view/' + userNo);
    },
  }
}
</script>

<style scoped>
.rank_list{
  width: 70%;
  margin: 20px 15% 20px 15%;
}
.rankTable {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.rankTable td, .rankTable th {
  border: 1px solid #ddd;
  padding: 8px;
}

.rankTable tr:nth-child(even){background-color: #f2f2f2;}

.rankTable tr:hover {background-color: #ddd;}

.rankTable th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: center;
  background-color: #04AA6D;
  color: white;
}

.rankTable td {
  text-align: center;
}
</style>