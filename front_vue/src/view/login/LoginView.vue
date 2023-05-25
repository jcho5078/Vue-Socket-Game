<template>
    <div>
      <h3>LOGIN</h3>
      <login-form/>
      {{this.$store.state.counter}}
    </div>
</template>

<script>
import LoginForm from "@/components/user/LoginForm";
export default {
  name: "LoginView",
  components: {LoginForm}
}
</script>

<style scoped>

</style>