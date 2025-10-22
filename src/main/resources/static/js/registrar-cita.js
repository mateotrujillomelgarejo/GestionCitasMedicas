const modal = document.getElementById('modalBuscarPaciente');
const btnOpenModal = document.getElementById('btnOpenModalPaciente');
const btnCloseModal = document.getElementById('btnCloseModalPaciente');
const inputBuscar = document.getElementById('inputBuscarPaciente');
const resultadoDiv = document.getElementById('resultadoPacientes');
const mensajeDiv = document.getElementById("mensajeExito");

// Abrir modal
btnOpenModal.addEventListener('click', () => {
  modal.classList.add('show');
  modal.setAttribute('aria-hidden', 'false');
  document.body.style.overflow = 'hidden';
  document.getElementById('inputBuscarPaciente').focus();
});

// Cerrar modal
btnCloseModal.addEventListener('click', () => {
  modal.classList.remove('show');
  modal.setAttribute('aria-hidden', 'true');
  document.body.style.overflow = '';
});

  if (mensajeDiv && mensajeDiv.textContent.trim() !== "") {
    mensajeDiv.style.display = "block";
    setTimeout(() => {
      mensajeDiv.style.display = "none";
    }, 5000);
  }

modal.addEventListener('click', (e) => {
  if (e.target === modal) {
    modal.classList.remove('show');
    document.body.style.overflow = '';
  }
});

inputBuscar.addEventListener("input", function () {
  let query = this.value.trim();
  if (query.length < 2) {
    resultadoDiv.innerHTML = "<p>Escriba al menos 2 caracteres...</p>";
    return;
  }

  fetch("/pacientes/buscar?query=" + encodeURIComponent(query))
    .then(res => res.text())
    .then(html => resultadoDiv.innerHTML = html)
    .catch(err => resultadoDiv.innerHTML = "<p>Error al buscar paciente</p>");
});

// Seleccionar paciente
function seleccionarPaciente(id, nombre, dni, historia) {
  document.querySelector('[name="paciente.idPaciente"]').value = id;
  document.querySelector('[name="paciente.nombreCompleto"]').value = nombre;
  document.querySelector('[name="paciente.dni"]').value = dni;
  document.querySelector('[name="paciente.historiaClinica"]').value = historia;

  document.getElementById('inputNombrePaciente').value = nombre;
  document.getElementById('inputDniPaciente').value = dni;
  document.getElementById('inputHistoriaClinica').value = historia;

  modal.classList.remove('show');
  document.body.style.overflow = '';
}

document.addEventListener('click', function(e) {
  if (e.target.classList.contains('seleccionar-btn')) {
    const id = e.target.getAttribute('data-id');
    const nombre = e.target.getAttribute('data-nombre');
    const dni = e.target.getAttribute('data-dni');
    const historia = e.target.getAttribute('data-historia');

    seleccionarPaciente(id, nombre, dni, historia);
  }
});
