document.getElementById('open-modalDevolucion-button').addEventListener('click', function() {
    fetch('/modalDevolucion')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modalDevolucion').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modalDevolucion').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modalDevolucion').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modalDevolucion');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});