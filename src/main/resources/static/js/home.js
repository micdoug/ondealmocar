console.log('inicializando')

const baseUrl = 'http://localhost:8080/api/votacao';

var app = new Vue({
    el: '#app',
    data: {
        votacoes: [],
        loading: false,
        selFuncionario: 0,
        selRestaurante: 0,
        funcionarios: [],
        restaurantes: []
    },
    methods: {
        loadFuncionarios: function() {
            this.loading = true
            this.$http.get('http://localhost:8080/api/funcionarios')
                .then(response => {
                    console.log(response)
                    this.funcionarios = response.body
                    this.loading = false

                }, response => {
                    this.loading = false
                })
        },
        loadRestaurantes: function() {
            this.loading = true
            this.$http.get('http://localhost:8080/api/restaurantes')
                .then(response => {
                    console.log(response)
                    this.restaurantes = response.body
                    this.loading = false

                }, response => {
                    this.loading = false
                })
        },
        search: function() {
            this.loading = true
            this.$http.get(baseUrl)
                .then(response => {
                    console.log(response)
                    this.votacoes = response.body
                    this.loading = false
                }, response => {
                    alert('Erro ao buscar dados')
                    console.log(response);
                    this.loading = false
                })
        },
        votar: function() {
            this.loading = true
            $('#modalVotar').modal('close')
            this.$http.post(baseUrl, { funcionarioId: this.selFuncionario, restauranteId: this.selRestaurante })
                .then(response => {
                    console.log(response)
                    this.loading = false
                    Materialize.toast("Votação realizada com sucesso.", 2000)
                }, response => {
                    console.log(response)
                    if (response.status == 400) {
                        alert(response.bodyText)
                    }
                    this.loading = false
                })
        }
    }
});
app.search();
app.loadFuncionarios();
app.loadRestaurantes();

$('document').ready(function() {
    $('#modalVotar').modal()
})