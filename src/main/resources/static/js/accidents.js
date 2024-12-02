function showAddAccidentForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новую аварию';
    document.getElementById('editForm').action = '/accident/add';
    clearFormFields();
}

function editAccident(accidentId, date, time, location, description) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать аварию';
    document.getElementById('editForm').action = '/accident/edit';
    document.getElementById('accidentId').value = accidentId;
    document.getElementById('date').value = date;
    document.getElementById('time').value = time;
    document.getElementById('location').value = location;
    document.getElementById('description').value = description;
}

function clearFormFields() {
    document.getElementById('accidentId').value = '';
    document.getElementById('date').value = '';
    document.getElementById('time').value = '';
    document.getElementById('location').value = '';
    document.getElementById('description').value = '';
}