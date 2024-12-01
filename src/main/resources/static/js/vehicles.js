function showAddVehicleForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новое ТС';
    document.getElementById('editForm').action = '/vehicle/add';
    clearFormFields();
}

function editVehicle(registrationNumber, model, manufacturer, yearOfManufacture, color, mileage, engineVolume, horsepower, registrationDate, registrationLocation, phone) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать ТС';
    document.getElementById('editForm').action = '/vehicle/edit';
    document.getElementById('registrationNumber').value = registrationNumber;
    document.getElementById('model').value = model;
    document.getElementById('manufacturer').value = manufacturer;
    document.getElementById('yearOfManufacture').value = yearOfManufacture;
    document.getElementById('color').value = color;
    document.getElementById('mileage').value = mileage;
    document.getElementById('engineVolume').value = engineVolume;
    document.getElementById('horsepower').value = horsepower;
    document.getElementById('registrationDate').value = registrationDate;
    document.getElementById('registrationLocation').value = registrationLocation;
    document.getElementById('phone').value = phone;
}

function clearFormFields() {
    document.getElementById('registrationNumber').value = '';
    document.getElementById('model').value = '';
    document.getElementById('manufacturer').value = '';
    document.getElementById('yearOfManufacture').value = '';
    document.getElementById('color').value = '';
    document.getElementById('mileage').value = '';
    document.getElementById('engineVolume').value = '';
    document.getElementById('horsepower').value = '';
    document.getElementById('registrationDate').value = '';
    document.getElementById('registrationLocation').value = '';
    document.getElementById('phone').value = '';
}