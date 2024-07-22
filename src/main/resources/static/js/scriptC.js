// Función para abrir el modal (para agregar o editar)
function openModal(id = null) {
    let url = id ? `/modalCliente/${id}` : '/modalCliente';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            
            const modal = document.getElementById('modalCliente');
            modal.style.display = 'block';

            // Configurar el título del modal
            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Cliente' : 'Agregar Nuevo Cliente';

            // Configurar la acción del formulario y el ID si es necesario
            const form = modal.querySelector('form');
            if (id) {
                form.action = '/api/cliente/actualizar';
                const idInput = form.querySelector('#id');
                if (idInput) {
                    idInput.value = id;
                }
            } else {
                form.action = '/api/cliente/guardar';
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
    const modal = document.getElementById('modalCliente');
    if (modal) {
        modal.remove();
    }
}

// Event listener para abrir el modal de agregar
document.getElementById('open-modalCliente-button').addEventListener('click', () => openModal());

// Event listener para abrir el modal de editar
document.querySelectorAll('.editar').forEach(button => {
    button.addEventListener('click', function() {
        const id = this.getAttribute('data-id');
        openModal(id);
    });
});

