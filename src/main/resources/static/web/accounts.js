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
                accountNumber:"",
                transactions:"",
                accountsFilterByStatus:"",
                accountType:"",
            }
        },
        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    this.accounts = response.data.accounts;                
                    this.loans = response.data.loans;
                    this.accounts = this.accounts.sort(function (a, b) { return a.id - b.id })
                    this.accountsFilterByStatus= this.accounts.filter(response=> response.accountState);
                    console.log(this.accountsFilterByStatus);
                    //*********************AMOUNT OF TRANSACTIONS***************************//
                    this.totalTransactions = 0;
                    this.accounts.forEach(element => {
                        this.totalTransactions += element.  transactions.length;
                        
                       
                        
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

                    //********************* GRAFICs ***************************//

                     var ctx = document.getElementById("myChart").getContext("2d");
                     var myChart = new Chart(ctx, {
                        type: "bar",
                        data: {
                            labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                            
                            datasets: [{
                                label: 'Balance per month',
                                color:'#fea55cfe',
                                data: [2500, 9000, 7500, 9500, 10000, this.totalBalance],                              
                                backgroundColor: [
                                '#3b8da5fe',
                                '#3cbce2fe',
                                '#2a9cbffe',
                                '#8ad6eefe',
                                '#caecf6fe',
                                '#3b8da5fe'],
                                borderColor: ['#fea55cfe'],
                                hoverBorderColor : ['#fff'],
                                hoverBackgroundColor : ['#fea55cfe'],
                                options: {
                                    plugins: {
                                        title: { 
                                            display: true,
                                            color: '#3b8da5fe'
                                        }
                                    }
                                }
                                
                            }
                            ]
                        },
                    })
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
                axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`)
                .then(response => Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account created successfully',
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
            deleteAccount (){ 
                axios.patch('/api/clients/current/account/state',`accountNumber=${this.accountNumber}`)
                .then(response => Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account delete successfully',
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
                    title: 'Accounts selected succesfully',               
                    showConfirmButton: false,
                    timer: 1500
                  })
            },
            selectAccountType(value){ 
                this.accountType = value
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: value + " " +"account selected",
                    showConfirmButton: false,
                    timer: 1500
                  })
            },
            moneyFormat (xxx){ 
                xxx = new Intl.NumberFormat('de-DE', { currency: 'USD', style: 'currency'  }).format(xxx)
                return xxx;
            }

            

        }
    }).mount('#app');


