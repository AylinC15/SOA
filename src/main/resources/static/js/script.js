document.getElementById('open-modal-button').addEventListener('click', function() {
    fetch('/modal')
        .then(response => response.text())
        .then(data => {
            const modalContainer = document.createElement('div');
            modalContainer.innerHTML = data;
            document.body.appendChild(modalContainer);
            document.getElementById('modal').style.display = 'block';

            document.getElementById('close-button').addEventListener('click', function() {
                document.getElementById('modal').remove();
            });

            document.getElementById('cancel-button').addEventListener('click', function() {
                document.getElementById('modal').remove();
            });

            window.onclick = function(event) {
                var modal = document.getElementById('modal');
                if (event.target == modal) {
                    modal.remove();
                }
            };
        });
});
