<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar Produto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <h1>Adicionar Produto</h1>

    <form id="formProduto" method="POST" enctype="multipart/form-data">
        <label for="nome">Nome do Produto:</label>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="descricao">Descrição do Produto:</label>
        <textarea id="descricao" name="descricao" required></textarea><br><br>

        <label for="componentes">Selecione os Componentes:</label><br>
        <label for="foto">Foto do Produto:</label>
        <input type="file" id="foto" name="foto" accept="image/*" required><br><br>

        <?php
        // Exibindo os componentes disponíveis para o produto (recuperado via API)
        $componentes = file_get_contents("http://localhost:8080/api/v1/componente");
        $componentes = json_decode($componentes, true); // Supondo que sua API retorna um JSON com a lista de componentes

        foreach ($componentes as $componente) {
            echo '<input type="checkbox" name="componentes[]" value="' . $componente['componente_id'] . '"> ' . $componente['nome'] . '<br>';
        }
        ?><br>

        <input type="submit" value="Adicionar Produto">
    </form>

    <script>
    document.getElementById("formProduto").addEventListener("submit", function(event) {
        event.preventDefault(); // Previne o envio padrão do formulário

        // Criando um FormData para enviar tanto os dados do produto quanto a foto
        const formData = new FormData();

        // Adicionando os campos do produto
        const nomeProduto = document.getElementById("nome").value;
        const descricaoProduto = document.getElementById("descricao").value;

        formData.append("nome", nomeProduto);
        formData.append("descricao", descricaoProduto);

        // Adicionando os componentes selecionados
        const componentes = Array.from(document.querySelectorAll('input[name="componentes[]"]:checked')).map(function(checkbox) {
            return checkbox.value;
        });
        formData.append("componentes", JSON.stringify(componentes)); // Envia os componentes como JSON

        // Adicionando a foto ao FormData e gerando o caminho
        const fotoInput = document.getElementById("foto");
        const fotoFile = fotoInput.files[0];

        if (fotoFile) {
            // Gerando o nome do arquivo e o caminho (diretório de uploads)
            const nomeArquivo = "foto_" + Date.now() + ".jpg";  // Nome do arquivo baseado no timestamp
            const caminhoFoto = "uploads/" + nomeArquivo;  // Caminho no diretório de uploads

            // Salve o arquivo no diretório 'uploads' do servidor
            const reader = new FileReader();
            reader.onload = function (e) {
                // Aqui, você pode salvar a imagem no frontend (no diretório de uploads).
                // O caminho da foto será enviado para o backend.

                // Envia o caminho da foto no FormData
                formData.append("foto", caminhoFoto); // Adiciona o caminho da foto no FormData

                // Enviando os dados do produto para a API
                fetch("http://localhost:8080/api/v1/produto", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        nome: nomeProduto,
                        descricao: descricaoProduto,
                        foto: caminhoFoto,  // Envia o caminho da foto
                        componentes: componentes // Envia os componentes como um array
                    })
                })
                .then(response => response.json())
                .then(data => {
                    if (data && data.produto_id) {
                        alert("Produto adicionado com sucesso!");
                        window.location.href = "listar_produtos.php"; // Redireciona para a página de produtos
                    } else {
                        alert("Erro ao adicionar produto.");
                    }
                })
                .catch(error => {
                    alert("Erro ao enviar o formulário.");
                    console.error("Erro:", error);
                });
            };
            reader.readAsDataURL(fotoFile); // Lê o arquivo para enviar a imagem
        } else {
            alert("Por favor, selecione uma foto.");
        }
    });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>
