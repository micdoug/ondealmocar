console.log('inicializando')

const baseUrl = 'http://localhost:8080/api/restaurantes';

var app = new Vue({
    el: '#app',
    data: {
        restaurantes: [],
        loading: false,
        nRestaurante: {
            nome: '',
            endereco: ''
        },
        editRestaurante: {
        }
    },
    methods: {
        search: function() {
            this.loading = true
            this.$http.get(baseUrl)
                .then(response => {
                    console.log(response)
                    this.restaurantes = response.body
                    this.loading = false
                }, response => {
                    console.log(response);
                    this.loading = false
                })
        },
        createRestaurante: function() {
            this.loading = true;
            $('#modal1').modal('close')
            this.$http.post(baseUrl, this.nRestaurante)
                    .then(response => {
                        this.loading = false;
                        this.nRestaurante = { nome: '', endereco: ''}
                        this.search();
                    }, response => {
                        this.loading = false;
                        this.nRestaurante = { nome: '', endereco: ''}
                        this.search();
                    });
        },
        editar: function(restaurante) {
            this.editRestaurante = _.clone(restaurante);
            $('#modalEditar').modal('open')
        },
        updateRestaurante: function() {
            this.loading = true;
            $('#modalEditar').modal('close')
            this.$http.put(baseUrl, this.editRestaurante)
                    .then(response => {
                        this.loading = false;
                        this.editRestaurante = { }
                        this.search();
                    }, response => {
                        this.loading = false;
                        console.log(response);
                        this.editRestaurante = { }
                        this.search();
                    });
        },
        deletar: function(restaurante) {
            this.loading = true;
            this.$http.delete(baseUrl+"/"+restaurante.id)
                    .then(response => {
                        this.loading = false;
                        this.search();
                    }, response => {
                        this.loading = false;
                        this.search();
                    });
        }
    }
});
app.search();

$('document').ready(function() {
    $('#modal1').modal()
    $('#modalEditar').modal()
})