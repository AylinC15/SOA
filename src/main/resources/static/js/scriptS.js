document.addEventListener('DOMContentLoaded', function() {
    // Código existente para abrir el modal de servicio
    document.getElementById('open-modalServicio-button').addEventListener('click', function() {
        fetch('/modalServicio')
            .then(response => response.text())
            .then(data => {
                const modalContainer = document.createElement('div');
                modalContainer.innerHTML = data;
                document.body.appendChild(modalContainer);
                document.getElementById('modalServicio').style.display = 'block';

                document.getElementById('close-button').addEventListener('click', function() {
                    document.getElementById('modalServicio').remove();
                });

                document.getElementById('cancel-button').addEventListener('click', function() {
                    document.getElementById('modalServicio').remove();
                });

                window.onclick = function(event) {
                    var modal = document.getElementById('modalServicio');
                    if (event.target == modal) {
                        modal.remove();
                    }
                };
            });
    });

    // Nuevo código para el modal de cotización
    const cotizacionBtn = document.getElementById('generar-cotizacion-button');
    if (cotizacionBtn) {
        cotizacionBtn.addEventListener('click', function() {
            // Crear y mostrar el modal de cotización
            const modalHTML = `
                <div id="cotizacion-modal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <h2>Generar Cotización</h2>
                        <form id="cotizacion-form">
                            <label for="id_empleado">ID del Empleado:</label>
                            <input type="number" id="id_empleado" name="id_empleado" required>
                            <button type="submit">Generar PDF</button>
                        </form>
                    </div>
                </div>
            `;
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = modalHTML;
            document.body.appendChild(modalContainer);

            const modal = document.getElementById('cotizacion-modal');
            modal.style.display = 'block';

            const span = modal.querySelector('.close');
            span.onclick = function() {
                modal.remove();
            }

            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.remove();
                }
            }

            const form = document.getElementById('cotizacion-form');
            form.onsubmit = function(e) {
                e.preventDefault();
                const idEmpleado = document.getElementById('id_empleado').value;
                generarCotizacion(idEmpleado);
                modal.remove();
            }
        });
    }
});

function generarCotizacion(idEmpleado) {
    fetch(`/api/servicio/cotizacion/${idEmpleado}`, { // Asegúrate de que la URL sea correcta
        method: 'GET',
        headers: {
            'Accept': 'application/pdf'
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.blob();
    })
    .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = `cotizacion_${idEmpleado}.pdf`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Error al generar la cotización. Por favor, verifica el ID del empleado e intenta de nuevo.\n\nDetalles: " + error.message);
    });
}