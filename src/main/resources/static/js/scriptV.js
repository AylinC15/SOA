// Función para abrir el modal (para agregar o editar)
function openModal(id = null) {
    let url = id ? `/modalVenta/${id}` : '/modalVenta';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            
            const modal = document.getElementById('modalVenta');
            modal.style.display = 'block';

            // Configurar el título del modal
            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Venta' : 'Agregar Nueva Venta';

            // Configurar la acción del formulario y el ID si es necesario
            const form = modal.querySelector('form');
            if (id) {
                form.action = '/api/venta/actualizar';
                const idInput = form.querySelector('#id');
                if (idInput) {
                    idInput.value = id;
                }
            } else {
                form.action = '/api/venta/guardar';
            }

            // Configurar eventos de cierre
            document.getElementById('close-button').addEventListener('click', closeModal);
            document.getElementById('cancel-button').addEventListener('click', closeModal);

            window.onclick = function(event) {
                if (event.target == modal) {
                    closeModal();
                }
            };
        });
}

// Función para cerrar el modal
function closeModal() {
    const modal = document.getElementById('modalVenta');
    if (modal) {
        modal.remove();
    }
}

// Event listener para abrir el modal de agregar
document.getElementById('open-modalVenta-button').addEventListener('click', () => openModal());

// Event listener para abrir el modal de editar
document.querySelectorAll('.editar').forEach(button => {
    button.addEventListener('click', function() {
        const id = this.getAttribute('data-id');
        openModal(id);
    });
});