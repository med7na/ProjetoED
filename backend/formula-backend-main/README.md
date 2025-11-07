Requisitos do backend >
Cadastro de produto e componentes, 
Explosão da estrutura, 
Cálculo total do produto, 
Atualização de preço de componentes e recálculo (implosão), 
CRUD em memória (apenas uma simulação de banco), 
Backend pronto para conectar com HTML + Banco depois

COMO COMPILAR E RODAR!
No terminal, dentro da pasta do projeto e no git bash vocês irão>

1) Compilar

mkdir bin
javac -d bin $(find pckproduto -name "*.java")

2) Executar
   
java -cp bin pckproduto.MainTest

!> Este backend já está pronto pra integração da parte de vocês.
Vocês só vão substituir o ProdutoDAOEmMemoria por um DAO com banco.
DAO = onde e como os dados são guardados

ProdutoDAOEmMemoria ➜ ProdutoDAOMySQL (ou outro banco)
