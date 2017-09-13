console.log('inicializando')

const baseUrl = 'http://localhost:8080/api/funcionarios';

var app = new Vue({
    el: '#app',
    data: {
        funcionarios: [],
        loading: false,
        nFuncionario: {
            nome: '',
            enabled: true
        },
        editFuncionario: {
        }
    },
    methods: {
        search: function() {
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
        createFuncionario: function() {
            this.loading = true;
            $('#modal1').modal('close');
            this.$http.post(baseUrl, this.nFuncionario)
                    .then(response => {
                        this.loading = false;
                        this.nFuncionario = { nome: '', isEnabled: true}
                        this.search();
                    }, response => {
                        this.loading = false;
                        this.nFuncionario = { nome: '', enabled: true}
                        console.log(response);
                        this.search();
                    });
        },
        editar: function(funcionario) {
            this.editFuncionario = _.clone(funcionario);
            $('#modalEditar').modal('open')
        },
        updateFuncionario: function() {
            this.loading = true;
            $('modalEditar').modal('close')
            this.$http.put(baseUrl, this.editFuncionario)
                    .then(response => {
                        this.loading = false;
                        this.editFuncionario = { }
                        this.search();
                    }, response => {
                        this.loading = false;
                        this.editFuncionario = { }
                        this.search();
                    });
        },
        deletar: function(funcionario) {
            this.loading = true;
            this.$http.delete(baseUrl+"/"+funcionario.id)
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