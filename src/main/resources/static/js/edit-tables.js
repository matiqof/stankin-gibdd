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