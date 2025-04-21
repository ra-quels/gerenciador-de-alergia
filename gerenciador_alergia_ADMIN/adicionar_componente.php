<?php

// Verifica se o formulário foi enviado
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = $_POST['nome'];

    // Chama a função de adicionar componente via API
    $resultado = adicionar_componente($nome);

    // Exibe uma mensagem de sucesso ou erro
    if ($resultado) {
        echo "<div class='alert alert-success text-center mt-3'>Componente adicionado com sucesso!</div>";
    } else {
        echo "<div class='alert alert-danger text-center mt-3'>Erro ao adicionar componente.</div>";
    }
}

/**
 * Função para adicionar um componente via API Java
 */
function adicionar_componente($nome) {
    $url = "http://localhost:8080/api/v1/componente"; // URL da API de criação de componente

    // Dados do componente a ser inserido
    $dados = array(
        "nome" => $nome
    );

    // Inicializa o cURL
    $ch = curl_init($url);

    // Configurações do cURL para enviar um POST com dados JSON
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Retorna a resposta como string
    curl_setopt($ch, CURLOPT_POST, true); // Método POST
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($dados)); // Dados do componente em formato JSON
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); // Cabeçalhos HTTP

    // Executa a requisição
    $response = curl_exec($ch);

    // Verifica se houve erro
    if(curl_errno($ch)) {
        echo 'Erro cURL: ' . curl_error($ch);
        curl_close($ch);
        return false;
    }

    // Fecha a conexão cURL
    curl_close($ch);

    // Converte a resposta JSON em um array associativo
    $resultado = json_decode($response, true);

    if ($resultado) {
        return true; // Componente adicionado com sucesso
    } else {
        return false; // Falha na adição
    }
}
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar Componente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* Estilos para o formulário */
        .container {
            max-width: 500px; /* Largura máxima do formulário */
            margin-top: 50px; /* Espaçamento superior */
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            font-weight: bold;
        }

        .alert {
            font-size: 1.1rem;
        }

        /* Centralizando o botão */
        .btn-center {
            display: block;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<?php include_once 'menu.php'; ?>

<div class="container">
    <h2 class="text-center mb-4">Adicionar Componente</h2>

    <!-- Formulário para adicionar componente -->
    <form action="adicionar_componente.php" method="POST">
        <div class="form-group row">
            <label for="nome" class="form-label">Nome do Componente:</label>
            <div class="col-12">
                <input type="text" id="nome" name="nome" class="form-control" required><br>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-12">
                <button type="submit" value="Adicionar Componente" class="btn btn-outline-primary btn-right mb-2">Salvar</button>
            </div>
        </div>
    </form>

    <div class="text-center">
        <a href="listar_componentes.php" class="btn btn-link">Ver Todos os Componentes</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
