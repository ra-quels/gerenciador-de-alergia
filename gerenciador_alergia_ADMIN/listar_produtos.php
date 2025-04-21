<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Produtos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .produto {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            transition: transform 0.2s ease-in-out;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 400px; /* Altura fixa para todas as caixas */
            background-color: #fff;
        }
        .produto:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .produto img {
            width: 100%;
            height: 200px;
            object-fit: cover; /* Isso vai garantir que a imagem cubra o espaço sem distorcer */
            border-radius: 8px;
        }
        .produto h3 {
            color: #2c3e50;
            font-size: 1.5rem;
            margin-bottom: 15px;
        }
        .produto p {
            font-size: 1rem;
            color: #7f8c8d;
        }
        .componentes {
            font-weight: bold;
            margin-top: 10px;
        }
        .no-components {
            color: #e74c3c;
        }
    </style>
</head>
<body>
<?php
    include_once 'menu.php'; 
?>

<div class="container mt-5">
    <div class="row">
        <?php
        // Recuperando a lista de produtos via API
        $produtos_json = file_get_contents("http://localhost:8080/api/v1/produto");
        $produtos = json_decode($produtos_json, true);

        if ($produtos && count($produtos) > 0) {
            // Loop para exibir cada produto
            foreach ($produtos as $produto) {
                echo "<div class='col-md-4'>";
                echo "<div class='produto'>";

                // Nome do produto
                echo "<h3>" . htmlspecialchars($produto['nome']) . "</h3>";

                // Descrição do produto
                echo "<p>" . htmlspecialchars($produto['descricao']) . "</p>";

                // Exibindo a imagem
                if (isset($produto['foto'])) {
                    echo "<img src='" . $produto['foto'] . "' alt='" . htmlspecialchars($produto['nome']) . "' class='img-fluid'>";
                } else {
                    echo "<p>Sem foto disponível.</p>";
                }

                // Buscar componentes diretamente pelo produto_id
                $produto_id = $produto['produto_id'];
                $componentes_json = file_get_contents("http://localhost:8080/api/v1/produto/$produto_id/componentes");
                $componentes = json_decode($componentes_json, true);

                // Exibindo os componentes
                if ($componentes && count($componentes) > 0) {
                    $componentes_texto = array_map(function($componente) {
                        return htmlspecialchars($componente['nome']);
                    }, $componentes);
                    $componentes_texto = implode(", ", $componentes_texto);
                    echo "<p class='componentes'><strong>Componentes:</strong> " . $componentes_texto . "</p>";
                } else {
                    echo "<p class='componentes no-components'><strong>Componentes:</strong> Nenhum componente associado.</p>";
                }

                echo "</div>";
                echo "</div>";
            }
        } else {
            echo "<p>Não há produtos disponíveis no momento.</p>";
        }
        ?>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>
