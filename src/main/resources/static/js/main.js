
//OBSŁUGA WYSZUKIWARKI(PRAWY GÓRNY RÓG)
$(document).ready(function(){
    $("#search-input").on("keyup", function() {
        var value = $(this).val().toLowerCase();
    $("#table>tbody>tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
});

    //DODAWANIE DZISIEJSZEJ DATY DO INPUTU W WUZETKACH
    document.getElementById('data-input').valueAsDate = new Date();

    //OBSŁUGA WIĘKSZEJ ILOŚĆ KLIENTÓW ZNAJDUJĄCYCH SIĘ W TABELI Z TARAMI
    var hiddenClassTd = $('.hidden');
    for(let td of hiddenClassTd) {
        td.addEventListener('mouseenter', function() { var klients = td.getElementsByClassName('off'); for(let klient of klients) { klient.style.display='block'};});
        td.addEventListener('mouseleave', function() { var klients = td.getElementsByClassName('off'); for(let klient of klients) { klient.style.display='none'};});
    }

    //DODANIE DO POLA NUMER WUZETKI NUMERU O JEDEN WIĘKSZEGO NA STARCIE
    var lastNumber = $("#table>tbody>tr>td")[0].textContent;
    $("#numer-input").val(parseInt(lastNumber.substr(0, lastNumber.indexOf('/'))) +1);
});

