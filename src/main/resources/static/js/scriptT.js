document.getElementById('open-modalTablero-button').addEventListener('click', function() {
    fetch('/modalTablero')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modalTablero').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modalTablero').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modalTablero').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modalTablero');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});