<?php

// Verifica se o formulário foi enviado
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nome = $_POST['nome'];
    $descricao = $_POST['descricao'];
    $foto = $_FILES['foto']; // Foto do produto
    $componentes = $_POST['componentes']; // Vetor de componentes

    // Chama a função de adicionar produto via API
    $resultado = adicionar_produto($nome, $descricao, $foto, $componentes);

    // Exibe uma mensagem de sucesso ou erro
    if ($resultado) {
        echo "<div class='alert alert-success mt-3 text-center'>Produto adicionado com sucesso!</div>";
    } else {
        echo "<div class='alert alert-danger mt-3 text-center'>Erro ao adicionar produto.</div>";
    }
}

/**
 * Função para adicionar um produto via API Java
 */
function adicionar_produto($nome, $descricao, $foto, $componentes) {
    $url = "http://localhost:8080/api/v1/produto"; // URL da API de criação de produto

    // Tratamento da foto (upload)
    $upload_dir = 'uploads/';
    $foto_nome = $upload_dir . time() . '_' . basename($foto['name']);
    
    if (!move_uploaded_file($foto['tmp_name'], $foto_nome)) {
        echo 'Erro ao fazer upload da foto.';
        return false;
    }

    // Dados do produto a ser inserido
    $dados = array(
        "nome" => $nome,
        "descricao" => $descricao,
        "foto" => $foto_nome, // Caminho da foto salva no servidor
        "componentes" => $componentes // Vetor de componentes
    );

    // Inicializa o cURL
    $ch = curl_init($url);

    // Configurações do cURL para enviar um POST com dados JSON
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Retorna a resposta como string
    curl_setopt($ch, CURLOPT_POST, true); // Método POST
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($dados)); // Dados do produto em formato JSON
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

    // Exibe a resposta da API para debug
    echo "<pre>Resposta da API: " . $response . "</pre>";

    // Converte a resposta JSON em um array associativo
    $resultado = json_decode($response, true);

    if ($resultado) {
        return true; // Produto adicionado com sucesso
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
    <title>Adicionar Produto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        /* Estilos personalizados para o formulário */
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            font-weight: bold;
        }
        .custom-checkbox label {
            font-size: 1rem;
        }
        .alert {
            text-align: center;
            font-size: 1.1rem;
        }
        /* Centralizando o conteúdo */
        .container {
            max-width: 600px; /* Limita a largura máxima do formulário */
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <?php
    include_once 'menu.php'; 
    ?>

    <div class="container">
        <h2 class="text-center mb-4">Adicionar Produto</h2>

        <!-- Formulário para adicionar produto -->
        <form action="adicionar_produto.php" method="POST" enctype="multipart/form-data">
            <div class="form-group row">
                <label for="nome" class="form-label">Nome:</label>
                <div class="col-12">
                    <input type="text" id="nome" name="nome" class="form-control" required><br>
                </div>
            </div>

            <div class="form-group row">
                <label for="descricao" class="form-label">Descrição:</label>
                <div class="col-12">
                    <textarea id="descricao" name="descricao" class="form-control" required></textarea><br>
                </div>
            </div>

            <div class="form-group row">
                <label for="foto" class="form-label">Foto do Produto:</label>
                <div class="col-12">
                    <input type="file" id="foto" name="foto" accept="uploads/*" class="form-control" required><br>
                </div>
            </div>

            <div class="form-group row">
                <label for="componentes" class="form-label">Selecione os Componentes:</label><br>
                <div class="col-12">
                    <?php
                    // Exibindo os componentes disponíveis para o produto (recuperado via API)
                    $componentes = file_get_contents("http://localhost:8080/api/v1/componente");
                    $componentes = json_decode($componentes, true); // Supondo que sua API retorna um JSON com a lista de componentes

                    foreach ($componentes as $componente) {
                        echo '<div class="form-check custom-checkbox">';
                        echo '<input class="form-check-input" type="checkbox" name="componentes[]" value="' . $componente['componente_id'] . '" id="componente' . $componente['componente_id'] . '">';
                        echo '<label class="form-check-label" for="componente' . $componente['componente_id'] . '">' . $componente['nome'] . '</label>';
                        echo '</div>';
                    }
                    ?>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-12 text-center">
                    <button type="submit" class="btn btn-outline-primary  mb-2">Salvar</button>
                </div>
            </div>
        </form>

        <a href="listar_produtos.php" class="btn btn-link mt-3 d-block text-center">Ver Todos os Produtos</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
