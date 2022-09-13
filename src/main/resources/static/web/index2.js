const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                firstName: "",
                lastName: "",
                email: "",
                password: "",
                firstNameRegister: "",
                lastNameRegister: "",
                emailRegister: "",
                passwordRegister: "",
                id: ""
            }
        },

        created() {





        },
        methods: {
            loginUser() {
                axios.post('/api/login', `email=${this.email}&password=${this.password}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(() => {
                        if (this.email.includes("@admin123")) {
                            location.href = "/web/manager.html"
                        } else {
                            location.href = "/web/accounts.html"
                        }
                    })
                    .catch(() => {
                        if (this.password=="") {

                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "empty password", })

                        } if (this.email=="") {

                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "empty email", })

                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "something went wrong ", })
                        }




                    })
                    
                    
                    },

                        signUp() {
                        axios.post('/api/clients', `firstName=${this.firstNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`)
                            .then(response => axios.post('/api/login', `email=${this.emailRegister}&password=${this.passwordRegister}`,))
                            .then(response => location.href = "/web/accounts.html")

                            .catch(response => Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "These email is already in use! Try whit a differt one!",
                            }))
                    },
        },
        }).mount('#app');






/* 




        if (this.email == "") {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: "Something wrong with the email!",
            })
        }
        if (this.password == "") {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: "Something wrong with the password!",
            })

        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: "Something wrong with the login!",
            })
        }

 */
