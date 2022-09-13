const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                accounts: [],
                loans: [],
                MatchIdOwnAccount:"",
                MatchIdThirdAccount:"",
                totalBalance: 0,
                totalTransactions: "",
                totalBalance: "",
                firstName: "",
                lastName: "",
                id: "",
                email: "",
                
                selectAccountOrigin:[],
                selectAccountDestiny:[],
                selectAmount: [],
                selectDescription:[],

                someoneSelectAccountOrigin:[],
                someoneSelectAccountDestiny:[],
                someoneSelectAmount:[],
                someoneSelectDescription:[],

            }
        },
        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    this.accounts = response.data.accounts;                
                    this.loans = response.data.loans;
                    this.accounts = this.accounts.sort(function (a, b) { return a.id - b.id })
                   

                    //*********************AMOUNT OF TRANSACTIONS***************************//
                    this.totalTransactions = 0;
                    this.accounts.forEach(element => {
                        this.totalTransactions += element.transaction.length
                        
                    });

                    //********************* TOTAL BALANCE **********************************//
                    let contador = 0;
                    this.accounts.forEach(element => {
                        contador += element.balance
                        this.totalBalance = contador
                    });

                    //*************************** DARK MODE *******************************//

                    const bdark = document.querySelector('#bdark');
                    const body = document.querySelector('body');

                    load();

                    bdark.addEventListener('click', e => {
                        body.classList.toggle('darkmode');
                        store(body.classList.contains('darkmode'))
                    });

                    function load() {
                        const darkmode = localStorage.getItem('darkmode')

                        if (!darkmode) {
                            store('false');
                        } else if (darkmode == 'true') {
                            body.classList.add('darkmode')
                        }
                    }
                    function store(value) {
                        localStorage.setItem('darkmode', value)
                    }

               
                   
                     
                 
                })},    

                    //********************* API EXCHANGE ***************************//

                    
                
             
        

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
                }
            },

            newDate(creationDate) {
                creationDate = new Date(creationDate).toLocaleString();
                return creationDate;
            },

            logOut() {
                axios.post('/api/logout')                             
                  .then (response =>location.href="/web/index.html")            
            },  
            signUp() {
                axios.post('/api/logout').then(response => location.href="/web/index.html")              
            },  
            createAccount (){ 
                axios.post('/api/clients/current/accounts')
                .then(response => Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account created successfully',
                    showConfirmButton: false,
                    timer: 1500
                  }))
                .then(x=>window.location.reload())
                .catch(response=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: "Maximum of accounts created!",                     
                  }));
            },
            transactionOwnAccount () {     
                axios.post('/api/transactions',`amount=${this.selectAmount}&description=${this.selectDescription}&originAccount=${this.selectAccountOrigin}&destinyAccount=${this.selectAccountDestiny}`)
                .then(respone=>Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Transaction done',
                    text:'Redirecting to Transactions Details',
                    showConfirmButton: false,
                    timer: 4500
                  }))
        
                  
              
                .then (response=> window.location.href=`./account.html?id=${this.MatchIdOwnAccount}`)
                .catch(error=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,                   
                  }))
            },
            transactionSomeoneAccount () {
                axios.post('/api/transactions',`amount=${this.someoneSelectAmount}&description=${this.someoneSelectDescription}&originAccount=${this.someoneSelectAccountOrigin}&destinyAccount=${this.someoneSelectAccountDestiny}`)
                .then(respone=>Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Transaction done',
                    text:'Redirecting to Transactions Details',
                    showConfirmButton: false,
                    timer: 4500
                  }))
                .then (response=> window.location.href=`./account.html?id=${this.MatchIdThirdAccount}`)
        
                .catch(error=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,                   
                  }))
            },
            

        },
        computed: {
            filterNameMatchIdOwnAccount (){ 
                this.MatchIdOwnAccount = this.accounts.filter(response => response.number==this.selectAccountOrigin).map(response=> response.id) 
                console.log(this.MatchIdOwnAccount);
            },
            filterNameMatchIdThirdAccount (){ 
                this.MatchIdThirdAccount = this.accounts.filter(response => response.number==this.someoneSelectAccountOrigin).map(response=> response.id)  
                console.log(this.MatchIdThirdAccount);

            },
        }
    }).mount('#app');


