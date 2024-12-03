function showAddCompositionForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новую связь';
    document.getElementById('editForm').action = '/accident-composition/add';
    clearFormFields();
}

function clearFormFields() {
    document.getElementById('accidentId').value = '';
    document.getElementById('registrationNumber').value = '';
}