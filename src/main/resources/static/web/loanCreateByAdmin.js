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
 
                valueLoan:0,
                newLoanName:"",
                newLoanMaxAmount:"",
                newLoanPayments:"",
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
            newLoan (){ 
                axios.post('/api/admin/loans',`name=${this.newLoanName}&maxAmount=${this.newLoanMaxAmount}&payments=${this.newLoanPayments}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(respone => Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Loan created successfully',
                    showConfirmButton: false,
                    timer: 1500
                }))
                .then (respone=> window.location.reload)               
                .catch(error=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: `${error.response.data}`,                     
                  }));
            }

        },
        computed: { 
        }
    }).mount('#app');


