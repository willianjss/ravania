async function obterClientes() {
    try {
        this.limparFormularios(); // Limpa os formulários antes de carregar os dados
        const resposta = await fetch("http://localhost:8080/clientes"); // Faz o pedido
        const clientes = await resposta.json(); // Converte para JSON

        const formatadorData = new Intl.DateTimeFormat('pt-BR');
        const respostaFormatada = clientes.map(item => {
            return {
                ...item, // Mantém os outros campos (codigo, status, etc)
                dataNascimento: formatadorData.format(new Date(item.dataNascimento))
            };
        });
        preencherTabelaClientes(respostaFormatada); // Chama a função para mostrar na tela
    } catch (erro) {
        console.error('Erro ao carregar dados dos clientes:', erro);
    }
}

function preencherTabelaClientes(dados) {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');

    // Percorremos cada utilizador do array JSON
    dados.forEach(cliente => {
        const linha = document.createElement('tr'); // Cria uma linha <tr>

        // Define o conteúdo da linha
        linha.innerHTML = `
                    <td>${cliente.codigo}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.dataNascimento}</td>
                    <td>${cliente.cpf}</td>
                `;

        corpoTabela.appendChild(linha); // Adiciona a linha ao corpo da tabela
    });
}

async function obterTransacoes() {
    this.limparFormularios(); // Limpa os formulários antes de carregar os dados
    try {
        const resposta = await fetch("http://localhost:8080/transacoes"); // Faz o pedido
        const transacoes = await resposta.json(); // Converte para JSON
        const formatadorMoeda = new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
        const formatadorData = new Intl.DateTimeFormat('pt-BR');
        const respostaFormatada = transacoes.map(item => {
            return {
                ...item, // Mantém os outros campos (codigo, status, etc)
                valor: formatadorMoeda.format(item.valor),
                dataTransacao: formatadorData.format(new Date(item.dataTransacao))
            };
        });
        preencherTabelaTransacoes(respostaFormatada); // Chama a função para mostrar na tela
    } catch (erro) {
        console.error('Erro ao carregar dados das transações:', erro);
    }
}

function preencherTabelaTransacoes(dados) {
    const corpoTabela = document.getElementById('tabela-transacoes-corpo');

    // Percorremos cada utilizador do array JSON
    dados.forEach(transacao => {
        const linha = document.createElement('tr'); // Cria uma linha <tr>

        // Define o conteúdo da linha
        linha.innerHTML = `
                    <td>${transacao.codigo}</td>
                    <td>${transacao.dataTransacao}</td>
                    <td>${transacao.status}</td>
                    <td>${transacao.valor}</td>
                    <td>${transacao.cvv}</td>
                    <td>${transacao.numeroCartao}</td>
                `;

        corpoTabela.appendChild(linha); // Adiciona a linha ao corpo da tabela
    });
}

obterClientes();
obterTransacoes();

async function obterClienteTransacao() {
    try {
        const resposta = await fetch("http://localhost:8080/clientes");
        const clientes = await resposta.json();
        preencherClienteTransacao(clientes);
    } catch (error) {
        console.error("Erro ao carregar a caixa de seleção", error)
    }
}

function preencherClienteTransacao(clientes) {
    // Limpar opções existentes (opcional, mas recomendado)
    const select = document.getElementById('cliente-transacao');
    select.innerHTML = '<option value="">Selecione um cliente</option>';

    // Percorrer o array de clientes diretamente
    clientes.forEach(cliente => {
        // Agora 'cliente' é o objeto real, ex: {nome: "João", codigo: 123}
        const novaOpcao = new Option(cliente.nome, cliente.codigo);
        select.options.add(novaOpcao);
    });
}

obterClienteTransacao();

const formClientes = document.getElementById("form-clientes");

formClientes.addEventListener('submit', function (event) {
    event.preventDefault(); // Impede o envio padrão e o recarregamento da página

    const dados = new FormData(formClientes); // Captura todos os campos automaticamente
    const json = JSON.stringify(Object.fromEntries(dados.entries())); // Converte para JSON

    fetch('http://localhost:8080/clientes/incluir', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: json
    })
        .then(response => response)
        .then(data => console.log('Sucesso:', data))
        .then(obterClientes)// Atualiza a tabela de clientes após o cadastro
        .catch(error => console.error('Erro:', error));
});

const formTransacoes = document.getElementById("form-transacao");

formTransacoes.addEventListener('submit', function (event) {
    event.preventDefault(); // Impede o envio padrão e o recarregamento da página

    const dados = new FormData(formTransacoes); // Captura todos os campos automaticamente
    const valor = document.getElementById('valor-transacao').value;
    // Remove os caracteres de formatação (R$, pontos e vírgula) para enviar apenas o número
    const valorLimpo = valor.replace(/[^0-9,-]+/g, '').replace(',', '.');
    dados.set('valor', parseFloat(valorLimpo)); // Atualiza o valor no FormData para o número limpo
    const json = JSON.stringify(Object.fromEntries(dados.entries())); // Converte para JSON

    fetch('http://localhost:8080/transacoes/incluir', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: json
    })
        .then(response => response)
        .then(data => console.log('Sucesso:', data))
        .then(obterTransacoes) // Atualiza a tabela de transações após o cadastro
        .catch(error => console.error('Erro:', error));
});

const cpfInput = document.getElementById('cpf-cliente');

cpfInput.addEventListener('input', function (e) {
    let valor = e.target.value;

    // 1. Remove qualquer caractere que não seja número
    valor = valor.replace(/\D/g, "");

    // 2. Aplica a formatação com Expressões Regulares (Regex)
    // O Regex identifica os grupos de números e adiciona a pontuação
    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");       // Coloca ponto após os primeiros 3 dígitos
    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");       // Coloca ponto após os segundos 3 dígitos
    valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2"); // Coloca hífen antes dos últimos 2 dígitos

    // 3. Devolve o valor formatado para o campo
    e.target.value = valor;
});

function limparFormularios() {
    document.getElementById('form-clientes').reset();
    document.getElementById('form-transacao').reset();
}

function formatarMoeda(campo) {
    // 1. Obtém o valor atual e remove caracteres não numéricos
    let valor = campo.value.replace(/\D/g, '');

    // 2. Converte para número e divide por 100 (para ter 2 casas decimais)
    valor = (valor / 100).toFixed(2);

    // 3. Formata o número para o padrão de moeda do Brasil
    if (isNaN(valor) || valor == 0) {
        campo.value = "";
    } else {
        campo.value = parseFloat(valor).toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
    }
}