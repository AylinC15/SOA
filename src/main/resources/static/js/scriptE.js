document.getElementById('open-modalEmpleado-button').addEventListener('click', function() {
    fetch('/modalEmpleado')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modalEmpleado').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modalEmpleado').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modalEmpleado').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modalEmpleado');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});