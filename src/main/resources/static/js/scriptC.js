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

            const modalTitle = modal.querySelector('#modalTitle');
            modalTitle.textContent = id ? 'Editar Cliente' : 'Agregar Nuevo Cliente';

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

            document.getElementById('close-button').addEventListener('click', closeModal);
            document.getElementById('cancel-button').addEventListener('click', closeModal);

            window.onclick = function(event) {
                if (event.target == modal) {
                    closeModal();
                }
            };
        });
}

function closeModal() {
    const modal = document.getElementById('modalCliente');
    if (modal) {
        modal.remove();
    }
}

function buscarCliente() {
    const searchTerm = document.getElementById('searchTerm').value;
    const searchType = document.getElementById('searchType').value;
    const tableBody = document.querySelector('table tbody');

    fetch(`/api/cliente/buscarCliente?termino=${encodeURIComponent(searchTerm)}&tipo=${searchType}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(clientes => {
            if (!Array.isArray(clientes)) {
                throw new Error('El resultado de la búsqueda debe ser una lista de clientes.');
            }

            tableBody.innerHTML = clientes.map(cliente => `
                <tr>
                    <td>${cliente.id_cliente}</td>
                    <td>${cliente.nombre}</td>
                    <td>${cliente.telefono}</td>
                    <td>${cliente.ruc}</td>
                    <td>${cliente.direccion}</td>
                    <td>
                        <div class="button-container">
                            <button class="btn editar" data-id="${cliente.id_cliente}">
                                <img src="img/editar.png" alt="Editar Icono">
                                Editar
                            </button>
                            <form action="/api/cliente/borrar/${cliente.nombre}" method="get">
                                <button class="btn eliminar">
                                    <img src="img/basura.png" alt="Eliminar Icono">
                                    Eliminar
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
            `).join('');
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
                    <td colspan="6">${error.message}</td>
                </tr>
            `;
        });
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('open-modalCliente-button').addEventListener('click', () => openModal());

    document.querySelectorAll('.editar').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            openModal(id);
        });
    });

    const searchTermInput = document.getElementById('searchTerm');
    searchTermInput.addEventListener('input', buscarCliente);

    const searchButton = document.getElementById('searchButton');
    if (searchButton) {
        searchButton.addEventListener('click', buscarCliente);
    }
});
