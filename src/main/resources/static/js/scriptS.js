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