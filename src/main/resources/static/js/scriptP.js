document.getElementById('open-modalProveedor-button').addEventListener('click', function() {
    fetch('/modalProveedor')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modalProveedor').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modalProveedor').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modalProveedor').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modalProveedor');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});