function showAddDrivingLicenseForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новое ВУ';
    document.getElementById('editForm').action = '/driving-license/add';
    clearFormFields();
}

function editDrivingLicense(licenseNumber, issueDate, expirationDate, departmentCode, phone) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать ВУ';
    document.getElementById('editForm').action = '/driving-license/edit';
    document.getElementById('licenseNumber').value = licenseNumber;
    document.getElementById('issueDate').value = issueDate;
    document.getElementById('expirationDate').value = expirationDate;
    document.getElementById('departmentCode').value = departmentCode;
    document.getElementById('phone').value = phone;
}

function clearFormFields() {
    document.getElementById('licenseNumber').value = '';
    document.getElementById('issueDate').value = '';
    document.getElementById('expirationDate').value = '';
    document.getElementById('departmentCode').value = '';
    document.getElementById('phone').value = '';
}