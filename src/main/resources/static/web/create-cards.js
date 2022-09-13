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
                totalTransactions: [],

                firstName: "",
                lastName: "",
                id: "",
                email: "",
                selectedColor: null,
                client: [],
                cards: [],
                debitCard: [],
                creditCard: [],
                cardType: "",
                cardColor: "",
                button: "",
                options: [
                    { text: 'SILVER', value: 'SILVER' },
                    { text: 'TITANIUM', value: 'TITANIUM' },
                    { text: 'GOLD', value: 'GOLD' }
                ],
                selectedType: null,
                options: [
                    { value: 'DEBIT', text: 'DEBIT', },
                    { text: 'CREDIT', value: 'CREDIT' },
                ]
            }
        },
        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    this.accounts = response.data.accounts;
                    this.loans = response.data.loans;
                    this.cards = response.data.cards;
                    this.accounts = this.accounts.sort(function (a, b) { return a.id - b.id })
                    let contadorTransactions = -1;
                    this.accounts.forEach(element => {
                        contadorTransactions += element.id
                        this.totalTransactions = contadorTransactions
                    });


                    //*********************DARK MODE ***************************//

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
                creationDate = new Date(creationDate).toLocaleString('es-AR', { day: "2-digit", month: '2-digit', year: '2-digit' });
                return creationDate;
            },
            newDate2(creationDate2) {
                creationDate2 = new Date(creationDate2).toLocaleString('es-AR', { month: '2-digit', year: '2-digit' });
                return creationDate2;
            },
            logOut() {

                axios.post('/api/logout').then(response => location.href = "/web/index.html")

            },       
            createCards() {
                axios.post('/api/clients/current/cards', `cardColor=${this.cardColor}&cardType=${this.cardType}`)
                     .then(respone => Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Card created successfully',
                        showConfirmButton: false,
                        timer: 1500
                    }))
                    .then(response =>location.href="/web/cards.html")            
            },
            allData(response) {
                this.client = response.data
            },
            newDate(creationDate) {
                return new Date(creationDate).toLocaleDateString('es-AR', { month: '2-digit', year: '2-digit' })
            },
            logOut() {
                axios.post('/api/logout')
                    .then(location.href = "./index.html")
            },
            typeCards() {
                this.cards.forEach(type => {
                    if (type.cardType == "CREDIT") {
                        this.creditCard.push(type)
                    }
                    if (type.cardType == "DEBIT") {
                        this.debitCard.push(type)
                    }
                })
            },
            selectValueFirtsColum(id1, id2) {
                let disableSectionColumn1 = document.getElementById(id2)
                let button = document.getElementById(id1)
                disableSectionColumn1.classList.remove("disableSection")
                if (button) {
                    button.classList.add("d-none")
                }
            },
            valueButtonType(type) {
                this.cardType = type
            },
            valueButtonColor(color) {
                this.cardColor = color
            },
            changeColor(color) {
                let frontCardColor = document.getElementById("frontCard")
                let backCardColor = document.getElementById("backCard")
                switch (color) {
                    case "TITANIUM":
                        if (frontCardColor.classList.contains('card__partGold') && backCardColor.classList.contains('card__partGold')) {
                            frontCardColor.classList.remove('card__partGold')
                            backCardColor.classList.remove('card__partGold')
                        }
                        if (frontCardColor.classList.contains('card__partSilver') && backCardColor.classList.contains('card__partSilver')) {
                            frontCardColor.classList.remove('card__partSilver')
                            backCardColor.classList.remove('card__partSilver')
                        }
                        frontCardColor.classList.add('card__partTitanium')
                        backCardColor.classList.add('card__partTitanium')
                        break;
                    case "SILVER":
                        if (frontCardColor.classList.contains('card__partGold') && backCardColor.classList.contains('card__partGold')) {
                            frontCardColor.classList.remove('card__partGold')
                            backCardColor.classList.remove('card__partGold')
                        }
                        if (frontCardColor.classList.contains('card__partTitanium') && backCardColor.classList.contains('card__partTitanium')) {
                            frontCardColor.classList.remove('card__partTitanium')
                            backCardColor.classList.remove('card__partTitanium')
                        }
                        frontCardColor.classList.add('card__partSilver')
                        backCardColor.classList.add('card__partSilver')
                        break;
                    case "GOLD":
                        if (frontCardColor.classList.contains('card__partSilver') && backCardColor.classList.contains('card__partSilver')) {
                            frontCardColor.classList.remove('card__partSilver')
                            backCardColor.classList.remove('card__partSilver')
                        }
                        if (frontCardColor.classList.contains('card__partTitanium') && backCardColor.classList.contains('card__partTitanium')) {
                            frontCardColor.classList.remove('card__partTitanium')
                            backCardColor.classList.remove('card__partTitanium')
                        }
                        frontCardColor.classList.add('card__partGold')
                        backCardColor.classList.add('card__partGold')
                        break;
                    default:
                        break;
                }
            },
            addDisableSection(id1, id2, id3) {
                let disableSectionColumn1 = document.getElementById(id2)
                let button1 = document.getElementById(id1)
                let button2 = document.getElementById(id3)
                disableSectionColumn1.classList.add("disableSection")
                if (button1) {
                    button1.classList.remove("d-none")
                }
                if (button2) {
                    button2.classList.remove("d-none")
                }
            },
        }
    }).mount('#app');
