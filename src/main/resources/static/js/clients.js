function showAddClientForm() {
    document.getElementById('clientModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить нового клиента';
    document.getElementById('clientForm').action = '/client/add';
    document.getElementById('phone').removeAttribute("readonly");
    document.getElementById('clientPassword').removeAttribute("readonly");
    clearFormFields();
    updatePasswordFieldRequired();
}

function editClient(phone, clientPassword, fullName, dateOfBirth, address, passportNumber, passportIssueDate, passportDepartmentCode, role) {
    document.getElementById('clientModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать клиента';
    document.getElementById('clientForm').action = '/client/edit';
    document.getElementById('phone').setAttribute("readonly", "");
    document.getElementById('clientPassword').setAttribute("readonly", "");
    document.getElementById('phone').value = phone;
    document.getElementById('clientPassword').value = clientPassword;
    document.getElementById('fullName').value = fullName;
    document.getElementById('dateOfBirth').value = dateOfBirth;
    document.getElementById('address').value = address;
    document.getElementById('passportNumber').value = passportNumber;
    document.getElementById('passportIssueDate').value = passportIssueDate;
    document.getElementById('passportDepartmentCode').value = passportDepartmentCode;
    document.getElementById('role').value = role;
}

function hideClientModal() {
    const modal = document.getElementById('clientModal');
    const modalContent = document.getElementById('clientModalContent');
    setAnimationToModal(modal, modalContent);
}

function clearFormFields() {
    document.getElementById('phone').value = '';
    document.getElementById('clientPassword').value = '';
    document.getElementById('fullName').value = '';
    document.getElementById('dateOfBirth').value = '';
    document.getElementById('address').value = '';
    document.getElementById('passportNumber').value = '';
    document.getElementById('passportIssueDate').value = '';
    document.getElementById('passportDepartmentCode').value = '';
    document.getElementById('role').value = '';
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

function updatePasswordFieldRequired() {
    const role = document.getElementById('role').value;
    const clientPassword = document.getElementById('clientPassword');

    if (role !== '' && role !== 'ROLE_USER') {
        clientPassword.setAttribute('required', '');
        clientPassword.removeAttribute('readonly');
    } else {
        clientPassword.removeAttribute('required');
        clientPassword.setAttribute('readonly', '');
    }
}

document.getElementById('role').addEventListener('change', updatePasswordFieldRequired);