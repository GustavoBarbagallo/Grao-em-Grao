/**
 * ==========================================================================
 * DE GRÃO EM GRÃO - SISTEMA DE GESTÃO DE BANCO DE ALIMENTOS
 * ARQUIVO JAVASCRIPT UNIFICADO
 * Preparado para integração com backend Java (REST/Spring Boot)
 * ==========================================================================
 */

document.addEventListener("DOMContentLoaded", function () {
    initSidebar();
    initLoginForm();
    initMovimentacao();
    initCadastroTabs();
    initStockFilters();
    initDashboardCharts();
    initFormHandlers();
    initPessoasActions();
});


/* ==========================================================================
   1. SIDEBAR DINÂMICA
   Injeta o menu lateral e marca o item ativo com base na URL atual.
   Itens: Dashboard, Itens que Vencem, Gestão de Pessoas, Cadastro de Produtos,
          Movimentação, Visualizar Estoque.
   (Distribuição e Registro Rápido foram unificados — não aparecem mais)
   ========================================================================== */
function initSidebar() {
    const sidebarContainer = document.getElementById("dynamic-sidebar");
    if (!sidebarContainer) return;

    const path = window.location.pathname.toLowerCase();

    const isActive = {
        dashboard:    path.includes("dashboard"),
        vencendo:     path.includes("vencendo") || path.includes("vencem"),
        pessoas:      path.includes("pessoas"),
        cadastro:     path.includes("cadastro") || path.includes("inventario") || path.includes("registro"),
        movimentacao: path.includes("movimentacao"),
        estoque:      path.includes("estoque"),
    };

    // Fallback: se nenhuma rota bater (ex: Live Server servindo "/"), ativa Dashboard
    if (!Object.values(isActive).some(Boolean)) {
        isActive.dashboard = true;
    }

    const nav = [
        { href: "./dashboard.html",      key: "dashboard",    icon: "grid_view",        label: "Dashboard" },
        { href: "./itens-vencendo.html",  key: "vencendo",     icon: "warning",          label: "Itens que Vencem" },
        { href: "./pessoas.html",         key: "pessoas",      icon: "people",           label: "Gestão de Pessoas" },
        { href: "./cadastro.html",        key: "cadastro",     icon: "add_box",          label: "Cadastro de Produtos" },
        { href: "./movimentacao.html",    key: "movimentacao", icon: "swap_horiz",       label: "Movimentação" },
        { href: "./estoque.html",         key: "estoque",      icon: "list_alt",         label: "Visualizar Estoque" },
    ];

    const navHTML = nav
        .map(({ href, key, icon, label }) => `
            <a href="${href}" class="side-item ${isActive[key] ? "active" : ""}">
                <span class="material-icons-outlined">${icon}</span> ${label}
            </a>`)
        .join("");

    sidebarContainer.innerHTML = `
        <div class="sidebar-header">
            <h2>De Grão em Grão</h2>
            <p>Sistema de Gestão</p>
        </div>
        <nav class="sidebar-nav">${navHTML}</nav>
        <div class="sidebar-footer">
            <a href="./index.html" class="side-item logout">
                <span class="material-icons-outlined">logout</span> Sair
            </a>
        </div>`;
}


/* ==========================================================================
   2. LOGIN
   Submit redireciona para o dashboard.
   Para integração backend: substituir o window.location por uma chamada
   POST /api/auth/login com { identifier, password } e tratar o JWT retornado.
   ========================================================================== */
function initLoginForm() {
    const loginForm = document.getElementById("login-form");
    if (!loginForm) return;

    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const identifier = document.getElementById("login-identifier").value.trim();
        const password    = document.getElementById("login-password").value;

        // TODO (backend): descomente e adapte quando a API estiver disponível
        // try {
        //     const res = await api.post("/auth/login", { identifier, password });
        //     localStorage.setItem("token", res.token);
        //     window.location.href = "./dashboard.html";
        // } catch (err) {
        //     showToast("Usuário ou senha inválidos.", "error");
        // }

        // Mock atual: redireciona direto
        window.location.href = "./dashboard.html";
    });
}


/* ==========================================================================
   3. MOVIMENTAÇÃO DE ESTOQUE
   Controla as abas Entrada / Saída e a seleção de tipo de saída.
   Para integração backend: POST /api/movimentacoes com o payload montado.
   ========================================================================== */
function initMovimentacao() {
    const tabEntrada = document.getElementById("tab-entrada");
    const tabSaida   = document.getElementById("tab-saida");
    const formEntrada = document.getElementById("form-entrada");
    const formSaida   = document.getElementById("form-saida");

    if (!tabEntrada || !tabSaida) return;

    // Troca de abas
    tabEntrada.addEventListener("click", () => {
        tabEntrada.classList.add("active");
        tabSaida.classList.remove("active");
        if (formEntrada) formEntrada.style.display = "block";
        if (formSaida)   formSaida.style.display   = "none";
    });

    tabSaida.addEventListener("click", () => {
        tabSaida.classList.add("active");
        tabEntrada.classList.remove("active");
        if (formSaida)   formSaida.style.display   = "block";
        if (formEntrada) formEntrada.style.display  = "none";
    });

    // Seleção visual de tipo de saída
    document.querySelectorAll(".btn-dest").forEach(btn => {
        btn.addEventListener("click", function () {
            document.querySelectorAll(".btn-dest").forEach(b => b.classList.remove("selected"));
            this.classList.add("selected");
            const hiddenInput = document.getElementById("tipo-saida-value");
            if (hiddenInput) hiddenInput.value = this.textContent.trim();
        });
    });

    // Submit entrada
    if (formEntrada) {
        formEntrada.addEventListener("submit", async function (e) {
            e.preventDefault();
            const payload = buildEntradaPayload(formEntrada);

            // TODO (backend):
            // await api.post("/api/movimentacoes/entrada", payload);

            console.log("[ENTRADA]", payload);
            showToast("Entrada registrada com sucesso!", "success");
            formEntrada.reset();
            // TODO: atualizar lista do histórico via GET /api/movimentacoes?limit=10
        });
    }

    // Submit saída
    if (formSaida) {
        formSaida.addEventListener("submit", async function (e) {
            e.preventDefault();
            const tipoSaida = document.getElementById("tipo-saida-value")?.value;
            if (!tipoSaida) {
                showToast("Selecione o tipo de saída antes de registrar.", "warning");
                return;
            }
            const payload = buildSaidaPayload(formSaida, tipoSaida);

            // TODO (backend):
            // await api.post("/api/movimentacoes/saida", payload);

            console.log("[SAÍDA]", payload);
            showToast("Saída registrada com sucesso!", "success");
            formSaida.reset();
            document.querySelectorAll(".btn-dest").forEach(b => b.classList.remove("selected"));
            document.getElementById("tipo-saida-value").value = "";
            // TODO: atualizar lista do histórico via GET /api/movimentacoes?limit=10
        });
    }
}

function buildEntradaPayload(form) {
    return {
        tipo: "ENTRADA",
        produto:    form.querySelector('input[placeholder*="Produto"]')?.value ?? "",
        lote:       form.querySelector('input[placeholder*="Lote"]')?.value ?? "",
        quantidade: form.querySelector('input[type="number"]')?.value ?? "",
        origem:     form.querySelector('input[placeholder*="Doador"]')?.value ?? "",
        dataHora:   new Date().toISOString(),
    };
}

function buildSaidaPayload(form, tipoSaida) {
    return {
        tipo: "SAIDA",
        produto:      form.querySelector('input[placeholder*="Produto"]')?.value ?? "",
        quantidade:   form.querySelector('input[type="number"]')?.value ?? "",
        tipoSaida:    tipoSaida,
        responsavel:  form.querySelector('input[placeholder*="Responsável"]')?.value ?? "",
        dataHora:     new Date().toISOString(),
    };
}


/* ==========================================================================
   4. CADASTRO DE PRODUTOS — ABAS (Novo Produto / Adicionar ao Inventário)
   A função switchCadastroTab também é chamada inline pelo HTML.
   ========================================================================== */
function initCadastroTabs() {
    // Nada a inicializar além dos handlers dos formulários — as abas são
    // controladas por onclick no HTML via switchCadastroTab() abaixo.

    const inventoryForm = document.getElementById("inventory-form");
    if (inventoryForm) {
        inventoryForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const payload = {
                nome:      document.getElementById("product-name")?.value ?? "",
                gtin:      document.getElementById("product-gtin")?.value ?? "",
                categoria: document.getElementById("product-category")?.value ?? "",
                unidade:   document.getElementById("product-unit")?.value ?? "",
                descricao: document.getElementById("product-description")?.value ?? "",
            };

            // TODO (backend): POST /api/produtos
            // await api.post("/api/produtos", payload);

            console.log("[NOVO PRODUTO]", payload);
            showToast("Produto cadastrado no catálogo com sucesso!", "success");
            inventoryForm.reset();
        });
    }

    const loteForm = document.getElementById("registro-rapido-form");
    if (loteForm) {
        loteForm.addEventListener("submit", async function (e) {
            e.preventDefault();
            const payload = {
                produto:    document.getElementById("reg-product")?.value ?? "",
                lote:       document.getElementById("reg-lote")?.value ?? "",
                validade:   document.getElementById("reg-validade")?.value ?? "",
                quantidade: document.getElementById("reg-quantidade")?.value ?? "",
                unidade:    document.getElementById("reg-unidade")?.value ?? "",
                doador:     document.getElementById("reg-doador")?.value ?? "",
                dataEntrada: new Date().toISOString(),
            };

            // TODO (backend): POST /api/estoque/lotes
            // await api.post("/api/estoque/lotes", payload);

            console.log("[NOVO LOTE]", payload);
            showToast("Lote adicionado ao inventário com sucesso!", "success");
            loteForm.reset();
        });
    }

    const btnScan = document.getElementById("btn-trigger-scan");
    if (btnScan) {
        btnScan.addEventListener("click", function () {
            // TODO (backend/device): integrar com leitor de câmera ou API de GTIN
            // Ex: GET /api/produtos/gtin/{code} para buscar produto já cadastrado
            const inputGtin = document.getElementById("product-gtin");
            if (inputGtin) {
                inputGtin.value = "7891000123456"; // simulação
                showToast("Código de barras simulado com sucesso!", "info");
            }
        });
    }
}

// Chamada via onclick no HTML da página de cadastro
function switchCadastroTab(tab, el) {
    document.querySelectorAll(".cadastro-painel").forEach(p => (p.style.display = "none"));
    document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
    const painel = document.getElementById("painel-" + tab);
    if (painel) painel.style.display = "block";
    el.classList.add("active");
}


/* ==========================================================================
   5. VISUALIZAÇÃO DE ESTOQUE — FILTROS
   Para integração backend: GET /api/estoque?search=&status=
   ========================================================================== */
function initStockFilters() {
    const searchInput  = document.getElementById("search-stock");
    const statusSelect = document.getElementById("filter-status");
    const tableRows    = document.querySelectorAll(".stock-table tbody tr");

    if (!tableRows.length) return;

    function filterTable() {
        const searchText     = searchInput?.value.toLowerCase() ?? "";
        const selectedStatus = statusSelect?.value.toLowerCase() ?? "todos";

        tableRows.forEach(row => {
            const productName = row.cells[0]?.textContent.toLowerCase() ?? "";
            const loteText    = row.cells[3]?.textContent.toLowerCase() ?? "";

            const badge = row.querySelector(".status-indicator");
            let rowStatus = "todos";
            if (badge) {
                if (badge.classList.contains("badge-ok"))      rowStatus = "ok";
                if (badge.classList.contains("badge-proximo")) rowStatus = "proximo";
                if (badge.classList.contains("badge-urgente")) rowStatus = "urgente";
            }

            const matchesSearch = productName.includes(searchText) || loteText.includes(searchText);
            const matchesStatus = selectedStatus === "todos" || rowStatus === selectedStatus;

            row.style.display = matchesSearch && matchesStatus ? "" : "none";
        });
    }

    if (searchInput)  searchInput.addEventListener("keyup", filterTable);
    if (statusSelect) statusSelect.addEventListener("change", filterTable);

    // TODO (backend): ao invés de filtrar no DOM, chamar:
    // GET /api/estoque?search={termo}&status={status}
    // e re-renderizar a tabela com os dados retornados
}


/* ==========================================================================
   6. DASHBOARD — GRÁFICOS
   Para integração backend: GET /api/dashboard/resumo para dados reais.
   ========================================================================== */
function initDashboardCharts() {
    const ctxBar = document.getElementById("barChart");
    if (ctxBar) {
        // TODO (backend): GET /api/dashboard/movimentacoes-mensais
        new Chart(ctxBar, {
            type: "bar",
            data: {
                labels: ["Jan", "Fev", "Mar", "Abr"],
                datasets: [
                    { label: "Entradas", data: [330, 300, 370, 350], backgroundColor: "#009661", borderRadius: 4 },
                    { label: "Saídas",   data: [280, 250, 310, 290], backgroundColor: "#3b82f6", borderRadius: 4 },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { position: "bottom" } },
            },
        });
    }

    const ctxPie = document.getElementById("pieChart");
    if (ctxPie) {
        // TODO (backend): GET /api/dashboard/categorias
        new Chart(ctxPie, {
            type: "pie",
            data: {
                labels: ["Grãos", "Enlatados", "Óleos", "Outros"],
                datasets: [{ data: [36, 26, 17, 21], backgroundColor: ["#009661", "#f36c13", "#3b82f6", "#a855f7"] }],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { position: "bottom" } },
            },
        });
    }
}


/* ==========================================================================
   7. GESTÃO DE PESSOAS — AÇÕES DE TABELA
   Para integração backend:
     - Editar: GET /api/pessoas/{id} → popular modal
     - Excluir: DELETE /api/pessoas/{id}
     - Novo Cadastro: POST /api/pessoas
   ========================================================================== */
function initPessoasActions() {
    const btnNovoCadastro = document.getElementById("btn-novo-cadastro");
    if (btnNovoCadastro) {
        btnNovoCadastro.addEventListener("click", function () {
            // TODO (backend): abrir modal de cadastro de nova pessoa
            showToast("Funcionalidade: abrir modal de cadastro.", "info");
        });
    }

    document.addEventListener("click", function (e) {
        const btn = e.target.closest("button");
        if (!btn) return;

        if (btn.classList.contains("btn-action-edit")) {
            // TODO (backend): GET /api/pessoas/{id} e abrir modal de edição
            showToast("Carregando dados para edição...", "info");
        }

        if (btn.classList.contains("btn-action-delete")) {
            const row  = btn.closest("tr");
            const nome = row?.cells[0]?.textContent.trim() ?? "este registro";
            if (confirm(`Deseja realmente remover "${nome}" do sistema?`)) {
                // TODO (backend): DELETE /api/pessoas/{id}
                // row.remove(); // descomentar após confirmar deleção no servidor
                showToast(`"${nome}" seria removido (integração pendente).`, "warning");
            }
        }
    });
}


/* ==========================================================================
   8. FORMULÁRIOS LEGADOS / OUTROS
   Handlers para formulários que ainda não têm módulo próprio.
   ========================================================================== */
function initFormHandlers() {
    // Formulário de movimentação legado (caso exista id="movimentacao-form" ainda)
    const movForm = document.getElementById("movimentacao-form");
    if (movForm) {
        movForm.addEventListener("submit", function (e) {
            e.preventDefault();
            showToast("Movimentação registrada!", "success");
            movForm.reset();
        });
    }
}


/* ==========================================================================
   9. UTILITÁRIOS
   ========================================================================== */

/**
 * Exibe uma notificação toast no canto da tela.
 * @param {string} message  Texto da mensagem
 * @param {"success"|"error"|"warning"|"info"} type  Tipo visual
 */
function showToast(message, type = "info") {
    const colors = {
        success: "#009661",
        error:   "#ea4335",
        warning: "#f36c13",
        info:    "#2b6cb0",
    };

    // Reutilizar container ou criar um novo
    let container = document.getElementById("toast-container");
    if (!container) {
        container = document.createElement("div");
        container.id = "toast-container";
        Object.assign(container.style, {
            position: "fixed",
            bottom:   "80px",    // acima da sidebar mobile
            right:    "16px",
            zIndex:   "9999",
            display:  "flex",
            flexDirection: "column",
            gap:      "8px",
        });
        document.body.appendChild(container);
    }

    const toast = document.createElement("div");
    Object.assign(toast.style, {
        background:   colors[type] ?? colors.info,
        color:        "#fff",
        padding:      "12px 18px",
        borderRadius: "6px",
        fontSize:     "14px",
        fontWeight:   "500",
        boxShadow:    "0 2px 8px rgba(0,0,0,0.18)",
        opacity:      "0",
        transform:    "translateY(8px)",
        transition:   "opacity 0.25s, transform 0.25s",
        maxWidth:     "320px",
        lineHeight:   "1.4",
    });
    toast.textContent = message;
    container.appendChild(toast);

    // Entrada
    requestAnimationFrame(() => {
        toast.style.opacity   = "1";
        toast.style.transform = "translateY(0)";
    });

    // Saída automática após 3s
    setTimeout(() => {
        toast.style.opacity   = "0";
        toast.style.transform = "translateY(8px)";
        setTimeout(() => toast.remove(), 280);
    }, 3000);
}

/**
 * Cliente HTTP base para integração com o backend Java (REST).
 * Uso: const data = await api.get("/produtos"); await api.post("/movimentacoes", payload);
 *
 * Configurar BASE_URL conforme o ambiente (dev / prod).
 */
const api = (() => {
    const BASE_URL = "http://localhost:8080/api"; // TODO: ajustar para URL de produção

    function getHeaders() {
        const token = localStorage.getItem("token");
        return {
            "Content-Type": "application/json",
            ...(token ? { Authorization: `Bearer ${token}` } : {}),
        };
    }

    async function request(method, endpoint, body) {
        const options = { method, headers: getHeaders() };
        if (body) options.body = JSON.stringify(body);

        const response = await fetch(`${BASE_URL}${endpoint}`, options);

        if (response.status === 401) {
            localStorage.removeItem("token");
            window.location.href = "./index.html";
            return;
        }

        if (!response.ok) {
            const err = await response.json().catch(() => ({}));
            throw new Error(err.message ?? `HTTP ${response.status}`);
        }

        const text = await response.text();
        return text ? JSON.parse(text) : null;
    }

    return {
        get:    (endpoint)        => request("GET",    endpoint),
        post:   (endpoint, body)  => request("POST",   endpoint, body),
        put:    (endpoint, body)  => request("PUT",    endpoint, body),
        delete: (endpoint)        => request("DELETE", endpoint),
    };
})();
