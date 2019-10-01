//METODY DO taryadd.html, OBSŁUGA PRZYPISYWANIA I USUWANIA KLIENTÓW DO SAMOCHODU
    function deleteKlient(klientId, samochodId) {
        var url = "/tary/deleteKlient/"+samochodId+"/"+klientId;
        console.log(url);
        $.ajax({
            url: url
        }).done(function(response) {
        console.log(response);
                $("#klientsList").replaceWith(response);
            });
    }

    function addKlient(samochodId) {
        var nazwa = $("#klient-input").val()
        var url = "/tary/addKlient/"+samochodId+"/"+nazwa;
        console.log(url);
        $.ajax({
            url: url
        }).done(function(response) {
        console.log(response);
                $("#klientsList").replaceWith(response);
            });
    }