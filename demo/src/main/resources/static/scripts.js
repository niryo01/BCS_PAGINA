document.addEventListener('DOMContentLoaded', function () {
  const mensajes = document.createElement('div');
  mensajes.id = 'mensaje-agregado';
  mensajes.style.position = 'fixed';
  mensajes.style.top = '10px';
  mensajes.style.right = '10px';
  mensajes.style.backgroundColor = '#28a745';
  mensajes.style.color = 'white';
  mensajes.style.padding = '10px 20px';
  mensajes.style.borderRadius = '5px';
  mensajes.style.display = 'none';
  document.body.appendChild(mensajes);

  document.querySelectorAll('.form-agregar').forEach(form => {
    form.addEventListener('submit', function (e) {
      e.preventDefault();

      const url = this.action + '?' + new URLSearchParams(new FormData(this)).toString();

      fetch(url)
        .then(response => response.text())
        .then(mensaje => {
          mensajes.textContent = mensaje;
          mensajes.style.display = 'block';
          setTimeout(() => mensajes.style.display = 'none', 3000);
          this.querySelector('input[name="cantidad"]').value = 1; // reset cantidad
        })
        .catch(error => {
          mensajes.textContent = 'Error al agregar producto';
          mensajes.style.backgroundColor = '#dc3545';
          mensajes.style.display = 'block';
          setTimeout(() => {
            mensajes.style.display = 'none';
            mensajes.style.backgroundColor = '#28a745';
          }, 3000);
        });
    });
  });
});
