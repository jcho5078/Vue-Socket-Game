<template>
  <div>
    <h3>SignUp</h3>
  </div>
  <div>
    <form method="post" enctype="application/x-www-form-urlencoded">
      ID : <input type="text" id="loginId">
      <br>
      PW : <input type="password" id="loginPw">
      <br>
      NICK NAME : <input type="text" id="userNm">
      <button type="submit" @submit.prevent="doSignUp">회원가입</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "SignUpForm",
  data() {
    return {

    }
  },
  methods: {
    doSignUp(){

    }
  }
}
</script>

<style scoped>

</style>