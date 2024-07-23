// Función para buscar productos
function buscarProducto() {
    const searchTerm = document.getElementById('searchTermProducto').value.trim();
    const tableBody = document.querySelector('table tbody');

    fetch(`/api/almacen/buscarProducto?name=${encodeURIComponent(searchTerm)}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(productos => {
            tableBody.innerHTML = productos.map(producto => `
                <tr>
                    <td>${producto.id_producto}</td>
                    <td>${producto.name}</td>
                    <td>${producto.marca}</td>
                    <td>${producto.precio}</td>
                    <td>${producto.cantidad}</td>
                    <td>${producto.nombre_proveedor}</td>
                    <td>
                        <div class="button-container">
                            <button class="btn editar" data-id="${producto.id_producto}">
                                <img src="img/editar.png" alt="Editar Icono">
                                Editar
                            </button>
                            <form action="/api/almacen/borrar/${producto.name}" method="get" style="display:inline;">
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
                    <td colspan="7">${error.message}</td>
                </tr>
            `;
        });
}

// Función para abrir el modal (para agregar o editar)
function openModal(id = null) {
    let url = id ? `/modal/${id}` : '/modal';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            
            const modal = document.getElementById('modal');
            modal.style.display = 'block';

            // Configurar el título del modal
            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Producto' : 'Agregar Nuevo Producto';

            // Configurar la acción del formulario y el ID si es necesario
            const form = modal.querySelector('form');
            if (id) {
                form.action = '/api/almacen/actualizar';
                const idInput = form.querySelector('#id');
                if (idInput) {
                    idInput.value = id;
                }
            } else {
                form.action = '/api/almacen/guardar';
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
    const modal = document.getElementById('modal');
    if (modal) {
        modal.remove();
    }
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Event listener para buscar productos en tiempo real
    const searchTermInput = document.getElementById('searchTermProducto');
    searchTermInput.addEventListener('input', buscarProducto);

    // Event listener para abrir el modal de agregar
    document.getElementById('open-modal-button').addEventListener('click', () => openModal());

    // Event listener para abrir el modal de editar desde la tabla
    document.querySelectorAll('.editar').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            openModal(id);
        });
    });

    // Cargar todos los productos al inicio
    buscarProducto();
});
