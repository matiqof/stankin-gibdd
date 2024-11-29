function showAddClientForm() {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Добавить нового клиента';
    document.getElementById('editForm').action = '/client/add';
    document.getElementById('phone').removeAttribute("readonly");
    document.getElementById('clientPassword').removeAttribute("readonly");
    clearFormFields();
    updatePasswordFieldRequired();
}

function editClient(phone, clientPassword, fullName, dateOfBirth, address, passportNumber, passportIssueDate, passportDepartmentCode, role) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').innerText = 'Редактировать клиента';
    document.getElementById('editForm').action = '/client/edit';
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