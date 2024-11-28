function showAddDrivingLicenseForm() {
    document.getElementById('drivingLicenseModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить новое ВУ';
    document.getElementById('drivingLicenseForm').action = '/driving-license/add';
    clearFormFields();
}

function editDrivingLicense(licenseNumber, issueDate, expirationDate, departmentCode, phone) {
    document.getElementById('drivingLicenseModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать ВУ';
    document.getElementById('drivingLicenseForm').action = '/driving-license/edit';
    document.getElementById('licenseNumber').value = licenseNumber;
    document.getElementById('issueDate').value = issueDate;
    document.getElementById('expirationDate').value = expirationDate;
    document.getElementById('departmentCode').value = departmentCode;
    document.getElementById('phone').value = phone;
}

function hideDrivingLicenseModal() {
    const modal = document.getElementById('drivingLicenseModal');
    const modalContent = document.getElementById('drivingLicenseModalContent');
    setAnimationToModal(modal, modalContent);
}

function clearFormFields() {
    document.getElementById('licenseNumber').value = '';
    document.getElementById('issueDate').value = '';
    document.getElementById('expirationDate').value = '';
    document.getElementById('departmentCode').value = '';
    document.getElementById('phone').value = '';
}

function hideInfoModal() {
    const modal = document.getElementById('infoModal');
    const modalContent = document.getElementById('infoModalContent');
    setAnimationToModal(modal, modalContent);
}

function setAnimationToModal(modal, modalContent) {
    modal.style.animationName = "fadeOut";
    modalContent.style.animationName = "slideOut";
    setTimeout(() => {
        modal.style.display = "none";
        modal.style.animationName = "fadeIn";
        modalContent.style.animationName = "slideIn";
    }, 290);
}

function sortClients() {
    const searchQuery = document.getElementById('searchQuery').value.toLowerCase();
    const table = document.getElementById('clientsTable');
    const rows = table.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {
        const row = rows[i];
        const cells = row.getElementsByTagName('td');
        let match = false;

        for (let j = 0; j < cells.length; j++) {
            const cell = cells[j];
            if (cell.innerText.toLowerCase().indexOf(searchQuery) > -1) {
                match = true;
                break;
            }
        }

        if (match) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    }
}