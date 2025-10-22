document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("modalDisponibilidadMedico");
  const btnOpen = document.getElementById("btnOpenModalMedico");
  const btnClose = document.getElementById("btnCloseModalMedico");
  const diaInput = document.getElementById("inputDiaConsulta");
  const horaInput = document.getElementById("inputHoraConsulta");
  const resultadoDiv = document.getElementById("resultadoMedicos");

  btnOpen.addEventListener("click", () => {
    modal.classList.add("show");
    modal.setAttribute("aria-hidden", "false");
    document.body.style.overflow = "hidden";
  });

  btnClose.addEventListener("click", () => {
    modal.classList.remove("show");
    modal.setAttribute("aria-hidden", "true");
    document.body.style.overflow = "";
  });

  document.getElementById("btnBuscarDisponibilidad").addEventListener("click", () => {
    const dia = diaInput.value.trim();
    const hora = horaInput.value.trim();

    if (!dia || !hora) {
      resultadoDiv.innerHTML = "<p>Seleccione día y hora.</p>";
      return;
    }

    fetch(`/medicos/disponibilidad?dia=${encodeURIComponent(dia)}&hora=${encodeURIComponent(hora)}`)
      .then(res => res.text())
      .then(html => {
        resultadoDiv.innerHTML = html;
      })
      .catch(() => {
        resultadoDiv.innerHTML = "<p>Error al cargar disponibilidad.</p>";
      });
  });

document.addEventListener("click", (e) => {
  if (e.target.classList.contains("seleccionar-medico-btn")) {
    const id = e.target.dataset.id;
    const nombre = e.target.dataset.nombre;
    const consultorio = e.target.dataset.consultorio;

    document.querySelector("[name='medico.idMedico']").value = id;
    document.querySelector("[name='medico.nombre']").value = nombre;
    document.querySelector("[name='medico.consultorio']").value = consultorio;

    document.getElementById("inputNombreMedico").value = nombre;
    document.getElementById("inputConsultorioMedico").value = consultorio;

    modal.classList.remove("show");
    modal.setAttribute("aria-hidden", "true");
    document.body.style.overflow = "";
  }
});

});
