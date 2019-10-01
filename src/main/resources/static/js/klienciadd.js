//METODY DO klienciadd.html, OBSŁUGA PRZYPISYWANIA I USUWANIA SAMOCHODÓW DO KLIENTA
    function deleteSamochod(samochodId, klientId) {
            var url = "/klienci/deleteSamochod/"+klientId+"/"+samochodId;
            console.log(url);
            $.ajax({
                url: url
            }).done(function(response) {
            console.log(response);
                    $("#samochodyList").replaceWith(response);
                });
        }

    function addSamochod(klientId) {
        var nazwa = $("#samochod-input").val()
        var url = "/klienci/addSamochod/"+klientId+"/"+nazwa;
        console.log(url);
        $.ajax({
            url: url
        }).done(function(response) {
        console.log(response);
                $("#samochodyList").replaceWith(response);
            });
    }