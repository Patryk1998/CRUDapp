//METODY DO wuzetki.html, OBSŁUGA PRZYCISKÓW ZAPŁACONE
    function deleteWuzetka(wuzetkaId, row) {
        var url = "/wuzetki/pay/"+wuzetkaId;

        $.ajax({
            url: url
        }).done(function() {
                    row.closest('tr').remove();
            });
    }

    function deleteWuzetki() {
        var url = "/wuzetki/pay";
        $.ajax({
            type: "POST",
            url: url,
            success: function(response) {
                location.assign(window.location.origin+'/wuzetki/all');
            }
        });
    }