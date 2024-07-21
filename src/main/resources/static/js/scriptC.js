document.getElementById('open-modalCliente-button').addEventListener('click', function() {
    fetch('/modalCliente')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modalCliente').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modalCliente').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modalCliente').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modalCliente');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});

