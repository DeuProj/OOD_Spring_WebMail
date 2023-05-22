/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

window.onload = function() {
    var addressString = document.getElementById('addressString').value;
    if (window.opener && !window.opener.closed && addressString) {
        var toInput = window.opener.document.getElementsByName('to')[0];
        if (toInput.value) {
            toInput.value += ', ';
        }
        toInput.value += addressString;
        window.close();
    }
};

