function sortClients() {
    const searchQuery = document.getElementById('searchQuery').value.toLowerCase();
    const table = document.getElementById('dataTable');
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

function hideEditModal() {
    const modal = document.getElementById('editModal');
    const modalContent = document.getElementById('editModalContent');
    setAnimationToModal(modal, modalContent);
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

document.addEventListener('DOMContentLoaded', () => {
    const table = document.getElementById('dataTable');
    const headers = table.querySelectorAll('th');
    const tbody = table.querySelector('tbody');
    const rows = tbody.querySelectorAll('tr');

    headers.forEach((header, index) => {
        header.addEventListener('click', () => {
            sortTable(index);
        });
    });

    function sortTable(columnIndex) {
        const rowsArray = Array.from(rows);
        if (columnIndex === rowsArray.length) {
            return;
        }

        rowsArray.sort((a, b) => {
            const aValue = a.querySelectorAll('td')[columnIndex].innerText;
            const bValue = b.querySelectorAll('td')[columnIndex].innerText;

            if (columnIndex === 3 || columnIndex === 5 || columnIndex === 6 || columnIndex === 7) {
                return aValue - bValue;
            } else if (columnIndex === 8) {
                return new Date(aValue) - new Date(bValue);
            } else {
                return aValue.localeCompare(bValue);
            }
        });

        tbody.innerHTML = '';
        rowsArray.forEach(row => tbody.appendChild(row));
    }
});