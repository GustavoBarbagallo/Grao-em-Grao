// 
// LÓGICA DA TELA DE REGISTRO DE INVENTÁRIO
// 
const inventoryForm = document.getElementById('inventory-form');

if (inventoryForm) {
    inventoryForm.addEventListener('submit', function(event) {
        // Impede a página de recarregar sumariamente
        event.preventDefault(); 
        
        // Captura os dados dos inputs (exatamente o que o back-end vai precisar)
        const productName = document.getElementById('product-name').value;
        const expiryDate = document.getElementById('expiry-date').value;
        const quantity = document.getElementById('quantity').value;

        // Simulação de Sucesso (Mock)
        alert(`SUCESSO (Simulação):\nO produto "${productName}" (${quantity} un.) vencendo em ${expiryDate} foi enviado para o sistema!`);
        
        // Limpa o formulário após o envio
        inventoryForm.reset();
    });
}

//
// LÓGICA DA TELA DE DISTRIBUIÇÃO
// 
function selectDestination(button) {
    // Remove a classe 'active' de todos os botões de destino
    document.querySelectorAll('.btn-dest').forEach(btn => btn.classList.remove('active'));
    
    // Adiciona a classe 'active' apenas no botão que o usuário clicou
    button.classList.add('active');
    
    // Pega o texto do botão clicado
    const destino = button.textContent.trim();
    console.log(`Destino selecionado para o próximo lote: ${destino}`);
}