<template>
    <!-- define the layout -->
    <div>
        <h4>Login</h4>
        <form>
            <div>
                <label for="studentId">Student ID</label>
                <input type="text" id="studentId" v-model="studentLoginRequest.studentId" />
            </div>
            <div>
                <label for="password">Password</label>
                <input type="password" id="password" v-model="studentLoginRequest.password" />
            </div>
        </form>
        <button type="submit" @click="login">Login</button>
        <p>{{ message }}</p>
    </div>
</template>

<script>
import LoginService from "../services/LoginService";

export default {
    name: "studentLogin",
    data() {
        return {
            studentLoginRequest: { studentId: "", password: "" },
            message: ""
        };
    },
    methods: {
        login() {
            LoginService.login(this.studentLoginRequest)
                .then(response => {
                    var student = response.data;
                    console.log(student);
                    localStorage.setItem("sid", student.id);
                    this.$router.push({ name: "login" });
                })
                .catch(e => {
                    this.studentLoginRequest.studentId = "";
                    this.studentLoginRequest.password = "";
                    this.message = e.response.data.message;
                    console.log(e.response.data);
                });
        }
    },
    mounted() {
        this.message = "";
    }
};
</script>

<style>
.edit-form {
    max-width: 300px;
    margin: auto;
}
</style>