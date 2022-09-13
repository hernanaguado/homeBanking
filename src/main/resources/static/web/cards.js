const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                accounts: [],
                loans: [],
                cards: [],
                totalBalance: [],
                totalTransactions:[],
                totalBalance: "",
                firstName: "",
                lastName: "",
                id:"",
                email: "",
                cardNumber:"",
                CardsFilterByStatus:"",
                dateNow:"",
                dateThruDate:"",
                accountNumber:"",
            }
        },
        created() {
                axios.get('/api/clients/current')
                    .then(response => {
                        this.clients = response.data;
                        this.accounts = response.data.accounts; 
                        this.loans = response.data.loans;   
                        this.cards = response.data.cards;     
                        this.CardsFilterByStatus = this.cards.filter(response=> response.stateOfCards == true);
                       
                        this.accounts = this.accounts.sort(function(a, b){return a.id-b.id})                       
                        let contadorTransactions = -1; 
                        this.accounts.forEach(element => {
                            contadorTransactions += element.id 
                            this.totalTransactions = contadorTransactions
                        });

                        let contador = 0; 
                        this.accounts.forEach(element => {
                            contador += element.balance 
                            this.totalBalance = contador                           
                        });
                            this.totalBalance= contador.toLocaleString('es-MX');
                            
                           //*********************date now ***************************//

                            this.dateNow = (new Date(Date.now())).toLocaleDateString();

                            this.newDate3() 

                           //*********************DARK MODE ***************************//

                        const bdark = document.querySelector('#bdark');
                        const body = document.querySelector('body');

                        load();
            
                        bdark.addEventListener('click', e => {
                            body.classList.toggle('darkmode');
                            store(body.classList.contains('darkmode'))
                        });

                        function load (){ 
                            const darkmode = localStorage.getItem('darkmode')
                            
                            if(!darkmode) { 
                                store('false');
                            }else if ( darkmode == 'true'){ 
                                body.classList.add('darkmode')
                            }
                        }
                        function store (value){ 
                            localStorage.setItem('darkmode',value)
                        }
                                                             
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
                axios.post('/api/clients', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,                   
                })                   
                    .catch(function (error) {
                        console.log(error);
                    })     
                }},
            newDate(creationDate) { 
                creationDate = new Date(creationDate).toLocaleString('es-AR', {day:"2-digit", month: '2-digit', year: '2-digit'});
                return creationDate;
            },   
            newDate2(creationDate2) { 
                creationDate2 = new Date(creationDate2).toLocaleString('es-AR', {month: '2-digit', year: '2-digit'});
                return creationDate2;
            },
            newDate3(value) { 
                return value = new Date(value).toLocaleDateString();                    
            },
            logOut() {

                axios.post('/api/logout').then(response => location.href="/web/index.html")
              
            },  
            deleteCard (){ 
                axios.patch('/api/clients/current/cards/state',`cardNumber=${this.accountNumber}`)
                .then(response => Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Card delete successfully',
                    showConfirmButton: false,
                    timer: 1500
                  }))
                .then(x=>window.location.reload())
                .catch(error=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: `${error.response.data}`,                     
                  }));
            },
            selectAccount (){ 
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Card selected succesfully',
                
                    showConfirmButton: false,
                    timer: 1500
                  })
            },
            
                
          
            
        }
    }).mount('#app');

