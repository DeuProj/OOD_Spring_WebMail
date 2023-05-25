/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

// checkFile.js
window.addEventListener('DOMContentLoaded', function() {
    var fileInput = document.getElementById('fileInput');
    var mailForm = document.getElementById('mailForm');

    mailForm.addEventListener('submit', function(e) {
        e.preventDefault();
        var file = fileInput.files[0];
        if (file) {
            new Promise((resolve, reject) => {
                var fileReader = new FileReader();
                fileReader.onload = resolve;
                fileReader.onerror = reject;
                fileReader.readAsDataURL(file);
            })
            .then(function() {
                // File is accessible, allow form submission
                mailForm.submit();
            })
            .catch(function() {
                // File is not accessible, prevent form submission
                alert('파일을 찾을 수 없습니다. 파일이 변경되었는지 확인해주세요.');
            });
        } else {
            // No file chosen, allow form submission
            mailForm.submit();
        }
    });
});
