// Función para abrir el modal (para agregar o editar)
function openModal(id = null) {
    let url = id ? `/modalProveedor/${id}` : '/modalProveedor';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            
            const modal = document.getElementById('modalProveedor');
            modal.style.display = 'block';
            // Configurar el título del modal
            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Proveedor' : 'Agregar Nuevo Proveedor';
            // Configurar la acción del formulario y el ID si es necesario
            const form = modal.querySelector('form');
            if (id) {
                form.action = '/api/proveedor/actualizar';
                const idInput = form.querySelector('#id');
                if (idInput) {
                    idInput.value = id;
                }
            } else {
                form.action = '/api/proveedor/guardar';
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
    const modal = document.getElementById('modalProveedor');
    if (modal) {
        modal.remove();
    }
}

// Función para buscar proveedores
function buscarProveedor() {
    const searchTerm = document.getElementById('searchTermProveedor').value.trim();
    const tableBody = document.querySelector('table tbody');

    console.log(`Buscando: ${searchTerm}`);

    fetch(`/api/proveedor/buscarProveedor?termino=${encodeURIComponent(searchTerm)}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(proveedores => {
            if (!Array.isArray(proveedores)) {
                throw new Error('El resultado de la búsqueda debe ser una lista de proveedores.');
            }

            tableBody.innerHTML = proveedores.map(proveedor => `
                <tr>
                    <td>${proveedor.id_proveedor}</td>
                    <td>${proveedor.nombre_proveedor}</td>
                    <td>${proveedor.telefono}</td>
                 
                    <td>
                        <div class="button-container">
                            <button class="btn editar" data-id="${proveedor.id_proveedor}">
                                <img src="img/editar.png" alt="Editar Icono">
                                Editar
                            </button>
                            <form action="/api/proveedor/borrar/${proveedor.nombre_proveedor}" method="get">
                                <button class="btn eliminar">
                                    <img src="img/basura.png" alt="Eliminar Icono">
                                    Eliminar
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
            `).join('');

            // Volver a añadir el event listener para el botón de editar
            document.querySelectorAll('.editar').forEach(button => {
                button.addEventListener('click', function() {
                    openModal(this.getAttribute('data-id'));
                });
            });
        })
        .catch(error => {
            console.error('Error:', error);
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5">${error.message}</td>
                </tr>
            `;
        });
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Event listener para buscar proveedores en tiempo real
    const searchTermInput = document.getElementById('searchTermProveedor');
    searchTermInput.addEventListener('input', buscarProveedor);

    // Event listener para abrir el modal de agregar
    document.getElementById('open-modalProveedor-button').addEventListener('click', () => openModal());

    // Event listener para abrir el modal de editar desde la tabla
    document.querySelectorAll('.editar').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            openModal(id);
        });
    });

    // Cargar todos los proveedores al inicio
    buscarProveedor();
});