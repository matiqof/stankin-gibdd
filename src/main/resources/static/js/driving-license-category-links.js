function showAddDrivingLicenseCategoryLinkForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новую связь';
    document.getElementById('editForm').action = '/driving-license-category-link/add';
    clearFormFields();
}

function clearFormFields() {
    document.getElementById('categoryName').value = '';
    document.getElementById('licenseNumber').value = '';
    document.getElementById('phone').value = '';
}