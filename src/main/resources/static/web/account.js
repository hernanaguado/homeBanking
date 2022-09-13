let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get("id");
const app = Vue.
    createApp({
        data() {
            return {
                message: "Hello Vue!",
                clients: [],
                accounts: [],
                accountsId: [],
                transactions: [],
                firstName: "",
                lastName: "",
                id: "",
                email: "",
                fromDate:"",
                toDate:"",
            }
        },
        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    this.accounts = response.data.accounts;
                    this.accountsId = this.accounts.find(account => account.id == urlName)
                    console.log(this.accountsId);
                    this.transactions = this.accountsId.transactions
                    console.log(this.transactions);



                    //********************* DARK MODE ***************************//

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
                .catch(function (error) {
                    console.log(error);
                })
        },
        methods: {
            newDate(creationDate) {
                creationDate = new Date(creationDate).toLocaleString();
                return creationDate;
            },
            logOut() {

               axios.post('/api/logout').then(response => location.href = "/web/index.html")
                
            },
            downloadPdf(){ 
                this.fromDate = new Date(this.fromDate).toISOString();        
                this.toDate = new Date(this.toDate).toISOString();  
                axios.post('/api/PDF',
                {
                    "fromDate":`${this.fromDate}`,
                    "finalDate":`${this.toDate}`,
                    "account":`${this.accountsId.number}`,
                   
                }      
                 )                        
                 .then(response => Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Download successfully, check your download folder',
                    showConfirmButton: false,
                    timer: 3500
                  }))
                  .then(response=> window.location.reload())                    
                .catch (error=> Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                  }))   
            },
        }
    }).mount('#app');

