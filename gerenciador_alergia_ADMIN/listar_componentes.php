<?php

// Função para obter todos os componentes via API Java
function listar_componentes() {
    $url = "http://localhost:8080/api/v1/componente"; // URL da API para listar todos os componentes

    // Inicializa o cURL
    $ch = curl_init($url);

    // Configurações do cURL para um GET
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Retorna a resposta como string
    curl_setopt($ch, CURLOPT_HTTPGET, true); // Método GET

    // Executa a requisição
    $response = curl_exec($ch);

    // Verifica se houve erro
    if(curl_errno($ch)) {
        echo 'Erro cURL: ' . curl_error($ch);
        curl_close($ch);
        return null;
    }

    // Fecha a conexão cURL
    curl_close($ch);

    // Converte a resposta JSON em um array associativo
    $componentes = json_decode($response, true);

    // Retorna os componentes
    return $componentes;
}

// Função para excluir um componente via API Java
function excluir_componente($componente_id) {
    $url = "http://localhost:8080/api/v1/componente/{$componente_id}"; // URL da API para excluir o componente

    // Inicializa o cURL
    $ch = curl_init($url);

    // Configurações do cURL para enviar uma requisição DELETE
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Retorna a resposta como string
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE"); // Método DELETE

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

    // Verifica se a exclusão foi bem-sucedida
    return true;
}

if (isset($_GET['excluir_id'])) {
    $componente_id = $_GET['excluir_id'];
    $resultado = excluir_componente($componente_id);
    if ($resultado) {
        echo "<div class='alert alert-success text-center mt-3'>Componente excluído com sucesso!</div>";
    } else {
        echo "<div class='alert alert-danger text-center mt-3'>Erro ao excluir componente.</div>";
    }
}

$componentes = listar_componentes();
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Componentes</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* Centralizando o conteúdo */
        .container {
            max-width: 900px; /* Largura máxima da tabela */
            margin-top: 50px; /* Espaçamento superior */
        }

        /* Estilos para a tabela */
        .table th, .table td {
            text-align: center;
        }

        .alert {
            font-size: 1.1rem;
        }

        .btn-center {
            display: block;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<?php include_once 'menu.php'; ?>

<div class="container">

    <h2 class="text-center mb-4">Lista de Componentes</h2>

    <!-- Mensagem de sucesso ou erro -->
    <?php if (isset($resultado)): ?>
        <div class="alert <?= $resultado ? 'alert-success' : 'alert-danger' ?> text-center">
            <?= $resultado ? 'Componente excluído com sucesso!' : 'Erro ao excluir componente.' ?>
        </div>
    <?php endif; ?>

    <!-- Tabela para exibir os componentes -->
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            <?php
            // Verifica se existem componentes para exibir
            if ($componentes && count($componentes) > 0) {
                foreach ($componentes as $componente) {
                    echo "<tr>";
                    echo "<td>" . htmlspecialchars($componente['componente_id']) . "</td>";
                    echo "<td>" . htmlspecialchars($componente['nome']) . "</td>";
                    echo "<td><a href='listar_componentes.php?excluir_id=" . $componente['componente_id'] . "' class='btn btn-outline-danger btn-sm' onclick='return confirm(\"Tem certeza que deseja excluir?\")'>Excluir <i class='bi bi-x-lg'></i></a></td>";
                    echo "</tr>";
                }
            } else {
                echo "<tr><td colspan='3' class='text-center'>Nenhum componente encontrado.</td></tr>";
            }
            ?>
        </tbody>
    </table>



</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
