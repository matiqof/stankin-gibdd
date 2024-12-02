function showAddFineForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новый штраф';
    document.getElementById('editForm').action = '/fine/add';
    clearFormFields();
}

function editFine(fineId, date, time, location, amount, type, description, article, vehicleRegistrationNumber) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать штраф';
    document.getElementById('editForm').action = '/fine/edit';
    document.getElementById('fineId').value = fineId;
    document.getElementById('date').value = date;
    document.getElementById('time').value = time;
    document.getElementById('location').value = location;
    document.getElementById('amount').value = amount;
    document.getElementById('type').value = type;
    document.getElementById('description').value = description;
    document.getElementById('article').value = article;
    document.getElementById('vehicleRegistrationNumber').value = vehicleRegistrationNumber;
}

function clearFormFields() {
    document.getElementById('fineId').value = '';
    document.getElementById('date').value = '';
    document.getElementById('time').value = '';
    document.getElementById('location').value = '';
    document.getElementById('amount').value = '';
    document.getElementById('type').value = '';
    document.getElementById('description').value = '';
    document.getElementById('article').value = '';
    document.getElementById('vehicleRegistrationNumber').value = '';
}