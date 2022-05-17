# Servidor-Cliente
Servidor-Cliente socket em java

Na src desse repositório possui dois arquivos, sendo o que roda o Cliente e outro que roda o Servidor para realizar uma conexão do tipo socket.

Para utilizar o cliente defina o IP e a porta no topo do código-fonte onde possui a declaração e instaciação de variáveis, para rodar no VSCode coloque para rodar o java, não para rodar o código, ou insira o caminho da pasta, uma dica é rodar o servidor e copiar o caminho de la trocando aepnas a última parte onde o nome está Servidor para Cliente.

Para rodar o servidor precisa apenas rodar o java mesmo que já irá iniciar uma conexão, também é possível trocar a porta do mesmo. No servidor a mensagem é filtrada quanto aos critérios e requisitos passados e devolve RESP200 apenas para a opção transferir, para o restante devolve ERRO400.
