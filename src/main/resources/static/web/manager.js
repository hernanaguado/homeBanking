const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                firstName: "",
                lastName: "",
                id:"",
                email: ""
            }
        },

        created() {

         
                axios.get('/api/clients')
                    .then(response => {
                        this.clients = response.data;
                        console.log(this.clients)
                    })

                    .catch(function (error) {
                        console.log(error);
                    })
           
        },
        methods: {

            
            addClient() {
                if (this.firstName.length == "" || this.lastName.length == "" || this.email.length == "") { 
                    return;
                } else { 
                    
                axios.post('/rest/clients', {email: this.email, firstName: this.firstName, lastName: this.lastName,})
                .then (response=> { 
                    window.location.reload()
                })
                console.log(this.firstName)          
                    
                    .catch(function (error) {
                        console.log(error);
                    });          
                    
                    
            
                
            }},
            
        }
    }).mount('#app');

