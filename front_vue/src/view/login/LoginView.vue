<template>
  <screen>
    <div>
      <h3>LOGIN</h3>
      <login-form/>
    </div>
  </screen>
</template>

<script>
import LoginForm from "@/components/LoginForm";
export default {
  name: "LoginView",
  components: {LoginForm}
}
</script>

<style scoped>

</style>