# De Grão em Grão

Sistema acadêmico de apoio à gestão de um banco de alimentos. A versão atual reúne uma landing page e um painel web demonstrativo com telas de estoque, cadastro, movimentação e consulta de pessoas.

## Objetivo e contexto

O projeto propõe uma interface para organizar informações relacionadas ao recebimento, armazenamento e destinação de alimentos. A iniciativa se relaciona à **ODS 2 — Fome Zero e Agricultura Sustentável**, ao tratar de gestão de alimentos e redução de desperdício como contexto do problema.

Essa relação descreve o propósito do projeto, não um impacto medido: a demonstração não comprova redução de desperdício, aumento da segurança alimentar ou resultados ambientais.

## Funcionalidades implementadas

- **Landing page:** apresenta a proposta do projeto, descreve o fluxo conceitual e direciona ao acesso do sistema.
- **Login demonstrativo:** valida localmente as credenciais `admin` e `admin123` e mantém a sessão na aba do navegador.
- **Navegação interna:** menu lateral no desktop, barra inferior em telas estreitas e indicação da página ativa.
- **Dashboard:** exibe indicadores, gráficos de exemplo e uma tabela estática de movimentações.
- **Estoque:** lista dados de exemplo e permite filtrar linhas por nome do produto, lote e status.
- **Itens próximos do vencimento:** apresenta cartões demonstrativos com quantidade, prazo e indicação visual de urgência.
- **Cadastro de produtos e lotes:** oferece formulários e alternância entre as telas de cadastro. O botão de leitura de código de barras preenche um valor simulado.
- **Movimentação:** permite alternar entre entrada e saída, selecionar um destino para a saída e validar essa seleção antes de exibir a confirmação demonstrativa.
- **Gestão de pessoas:** apresenta uma tabela de exemplo e ações de interface para cadastro, edição e exclusão, sem persistir alterações.
- **Layout responsivo:** adapta as páginas internas para telas menores, incluindo tabelas com rolagem horizontal quando necessário.

## Capturas de tela

As capturas disponíveis estão na pasta `assets/`. Ainda não há uma captura da página de estoque no repositório.

| Landing page | Dashboard |
|---|---|---|
| ![Captura da landing page](<./assets/captura (2).png>) | ![Captura do dashboard](<./assets/captura (1).png>) |

## Tecnologias

### Frontend

- HTML5, CSS3 e JavaScript.
- Chart.js para os gráficos demonstrativos do dashboard, carregado por CDN.
- Material Icons Outlined para ícones, carregados do Google Fonts.
- Inter e Poppins para tipografia, carregadas do Google Fonts.
- Visual Studio Code com a extensão Live Server para servir as páginas localmente.

### Backend incluído no repositório

- Java 17 e Spring Boot 3.2.
- Spring Web para endpoints REST, Spring Data JPA e Jakarta Validation.
- Maven para gerenciamento e execução do projeto Java.
- Dependências para H2 e MySQL.

O frontend demonstrativo não consome atualmente esses endpoints. A configuração e a execução integradas do backend e do banco de dados dependem de ajustes locais e não são necessárias para visualizar a demonstração.

## Estrutura do repositório

```text
.
├── assets/                 # Imagens já existentes no repositório
├── backend/
│   ├── src/main/java/      # Controllers, serviços, modelos e repositórios Java
│   ├── src/main/resources/ # Configuração da aplicação
│   └── pom.xml             # Dependências e configuração Maven
├── docker/                 # Arquivos opcionais de configuração do banco de dados
├── frontend/
│   ├── index.html          # Landing page
│   ├── login.html          # Login demonstrativo
│   ├── dashboard.html      # Dashboard
│   ├── estoque.html        # Consulta de estoque
│   ├── itens-vencendo.html # Itens próximos do vencimento
│   ├── cadastro.html       # Cadastro de produtos e lotes
│   ├── movimentacao.html   # Entrada e saída de estoque
│   ├── pessoas.html        # Gestão demonstrativa de pessoas
│   ├── css/                # Estilos das páginas internas
│   ├── js/                 # Comportamentos do frontend
│   └── styles/             # Estilos da landing page
└── readme.md
```

## Executar a demonstração no VS Code

1. Abra a pasta do projeto no Visual Studio Code.
2. Instale a extensão **Live Server**, caso ainda não esteja instalada.
3. No explorador de arquivos, abra `frontend/index.html` e selecione **Open with Live Server**.
4. Na landing page, acesse **Acessar Portal / Login**. Se preferir, abra `frontend/login.html` diretamente pelo Live Server.
5. Use as credenciais de demonstração indicadas abaixo.

As páginas carregam bibliotecas, fontes e ícones de serviços externos. Para exibi-los, é necessária uma conexão com a internet; o projeto não adiciona pacotes ao frontend.

## Credenciais de demonstração

```text
Usuário: admin
Senha:   admin123
```

## Login e dependência do backend

O login acima é exclusivamente demonstrativo: a validação é feita no JavaScript do navegador, e a sessão fica em `sessionStorage`. Ele não autentica uma conta no backend. Portanto, não deve ser usado como mecanismo de segurança ou como autenticação de produção.

O dashboard, os registros das tabelas e os cartões exibem conteúdo de exemplo. Os filtros de estoque, a navegação, a troca de abas, a seleção do destino e as validações visuais funcionam no frontend. O envio dos formulários apresenta mensagens de sucesso e reinicia os campos, mas não salva dados. A leitura de código de barras é simulada; cadastro, edição e exclusão de pessoas também não são persistidos.

O repositório contém uma API Spring Boot com endpoints de itens, movimentações, pessoas e indicadores do dashboard. Embora exista um cliente HTTP base no JavaScript, as telas descritas acima não estão conectadas a essa API. Executar o backend não transforma os dados demonstrativos em dados persistidos. A inicialização da API depende de configurar e validar o datasource e o banco de dados no ambiente local; este README não pressupõe que essa integração esteja pronta.

## Limitações da versão demonstrativa

- Os dados apresentados nas páginas internas são exemplos estáticos e não constituem registros reais.
- Formulários, operações de cadastro e movimentação não gravam informações nem atualizam o estoque.
- O login utiliza credenciais fixas no frontend e não oferece autenticação segura.
- O leitor de código de barras e as ações de cadastro, edição e exclusão de pessoas são simulações ou avisos de interface.
- Os gráficos usam valores definidos no código e não representam indicadores calculados a partir de uma base conectada.
- O backend e o banco de dados exigem configuração e validação independentes; não são necessários para executar a demonstração pelo Live Server.
