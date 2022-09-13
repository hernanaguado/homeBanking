const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                accounts: [],
                loans: [],
                totalBalance: 0,
                totalTransactions: "",
                totalBalance: "",
                firstName: "",
                lastName: "",
                id: "",
                email: "",
                interestCalc:"",

                valueLoan:0,
                loanAmount:[],
                loanAcountDestiny:"",
                loanInstalment:[],

                loanData:[],
            }
        },
        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    this.accounts = response.data.accounts;                
                    this.loans = response.data.loans;
                    this.accounts = this.accounts.sort(function (a, b) { return a.id - b.id })
                 

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
                })
            //*************************** LOAN INFORMATION *******************************//
            
            axios.get('/api/loans')
            .then(response => { 
                this.loanData = response.data 
                                           
                console.log(this.loanData)
            },
               



            )

            
            
            
            },              
                   

                    
                
             
        

        methods: {

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
            loanValue(value) {
                this.valueLoan = value
            },
            loanAply(){ 

                axios.post('/api/client/loans',
                                       
                {
                    "id":`${this.valueLoan}`,
                    "amount":`${this.loanAmount}`,
                    "payments":`${this.loanInstalment}`,
                    "accountDestination":`${this.loanAcountDestiny}`
                }      
                 )                        
                .then(response =>location.href="/web/accounts.html")                   
                .catch (response=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: "Something dosen't match!",
                  }))   
            },
           

            

        },
        computed: { 

             interestPlus() { 

                this.interestCalc = this.loanAmount * 0.20
           
            }
        }
    }).mount('#app');


