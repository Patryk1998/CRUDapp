//METODY DO cennikParts.html, OBSŁUGA DODAWANIA I EDYCJI CEN
    function dodajEdytujCena() {
        var url = '/ceny/change/'+$("#klient-select option:selected").val();
        if($("#cena-input").val()=='') $("#cena-input").css("border-color", "red");
        else {
            $.ajax({
                method: "POST",
                url: url,
                data: $('#cena-form').serialize(),
                success: function(response){
                    $("#cenyList").html(response);
                }
            });
        }
    };

    function deleteCena(cenaId) {
        var url = '/ceny/delete/'+cenaId;
        $.ajax({
            method: "GET",
            url: url,
            success: function(response){
                $("#cenyList").html(response);
            }
        });

    }