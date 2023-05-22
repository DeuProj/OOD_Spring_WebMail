/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.querySelectorAll('.delete-link').forEach((link) => {
    link.addEventListener('click', (event) => {
        event.preventDefault();
        let url = '/delete_addrbook.do?email=' + event.target.id;
        if (confirm('정말로 삭제하시겠습니까?')) {
            window.location.href = url;
        }
    });
});
