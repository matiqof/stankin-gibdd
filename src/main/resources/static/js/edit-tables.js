function filterData() {
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

        if (columnIndex === rowsArray[0].querySelectorAll('td').length - 1) {
            return;
        }

        rowsArray.sort((a, b) => {
            const aValue = a.querySelectorAll('td')[columnIndex].innerText;
            const bValue = b.querySelectorAll('td')[columnIndex].innerText;

            if (aValue === '' || aValue === 'null' || aValue === 'undefined') {
                return 1;
            }
            if (bValue === '' || bValue === 'null' || bValue === 'undefined') {
                return -1;
            }

            if (isNumeric(aValue) && isNumeric(bValue)) {
                return parseFloat(aValue) - parseFloat(bValue);
            } else if (isDate(aValue) && isDate(bValue)) {
                return new Date(aValue) - new Date(bValue);
            } else {
                return aValue.localeCompare(bValue);
            }
        });

        tbody.innerHTML = '';
        rowsArray.forEach(row => tbody.appendChild(row));
    }

    function isNumeric(value) {
        return !isNaN(parseFloat(value)) && isFinite(value);
    }

    function isDate(value) {
        const date = new Date(value);
        return !isNaN(date.getTime());
    }
});