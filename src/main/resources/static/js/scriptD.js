function buscarDevolucion() {
    const searchTerm = document.getElementById('searchTermDevolucion').value.trim();
    const tableBody = document.querySelector('table tbody');

    fetch(`/api/devolucion/buscarDevolucion?producto=${encodeURIComponent(searchTerm)}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(devoluciones => {
            tableBody.innerHTML = devoluciones.map(devolucion => `
                <tr>
                    <td>${devolucion.id_devolucion}</td>
                    <td>${devolucion.fecha}</td>
                    <td>${devolucion.producto}</td>
                    <td>${devolucion.nombre}</td>
                    <td>
                        <div class="button-container">
                            <button class="btn editar" data-id="${devolucion.id_devolucion}">
                                <img src="img/editar.png" alt="Editar Icono">
                                Editar
                            </button>
                            <form action="/api/devolucion/borrar/${devolucion.id_devolucion}" method="get" style="display:inline;">
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

// Función para abrir el modal (para agregar o editar)
function openModal(id = null) {
    let url = id ? `/modalDevolucion/${id}` : '/modalDevolucion';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            
            const modal = document.getElementById('modalDevolucion');
            modal.style.display = 'block';

            // Configurar el título del modal
            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Devolución' : 'Agregar Nueva Devolución';

            // Configurar la acción del formulario y el ID si es necesario
            const form = modal.querySelector('form');
            if (id) {
                form.action = '/api/devolucion/actualizar';
                const idInput = form.querySelector('#id');
                if (idInput) {
                    idInput.value = id;
                }
            } else {
                form.action = '/api/devolucion/guardar';
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
    const modal = document.getElementById('modalDevolucion');
    if (modal) {
        modal.remove();
    }
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Event listener para buscar devoluciones en tiempo real
    const searchTermInput = document.getElementById('searchTermDevolucion');
    searchTermInput.addEventListener('input', buscarDevolucion);

    // Event listener para abrir el modal de agregar
    document.getElementById('open-modalDevolucion-button').addEventListener('click', () => openModal());

    // Event listener para abrir el modal de editar desde la tabla
    document.querySelectorAll('.editar').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            openModal(id);
        });
    });

    // Cargar todas las devoluciones al inicio
    buscarDevolucion();
});
