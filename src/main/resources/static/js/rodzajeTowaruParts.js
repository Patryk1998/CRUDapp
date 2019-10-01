//METODY DO rodzajeTowaruParts.html, OBSŁUGA USTAWIENIA>EDYCJA RODZAJÓW TOWARU
    function addRodzaj() {
        var url = '/rodzaje/add';
        if($("#cena-input").val()=='') $("#cena-input").css("border-color", "red");
        else {
            $.ajax({
                method: "POST",
                url: url,
                data: $('#rodzaj-form').serialize(),
                success: function(response){
                    $("#rodzajeList").html(response);
                }
            });
        }
    };

    function deleteRodzaj(rodzajId) {
            var url = '/rodzaje/delete/'+rodzajId;
            $.ajax({
                method: "GET",
                url: url,
                success: function(response){
                console.log(response);
                    if(response=="<div>.</div>") ;
                    else $("#rodzajeList").html(response);
                },
                error: function(response) {
                    location.assign(window.location.origin+'/settings');
                }
            });
        }

        function updateRodzaj(id) {
            var td = $('#'+id);
            console.log(td[0].contentEditable);
            if(td[0].contentEditable=='false') {
                allNonEditable();
                td.attr('contenteditable','true');
                td.css('background-color','yellow');
            } else {
                var text = td.text();
                var data = {nazwa: text};
                var url = '/rodzaje/update/'+id;
                 $.ajax({
                    method: "POST",
                    url: url,
                    data: data,
                    success: function(response){
                        $("#rodzajeList").html(response);
                    }
                });
            }
        }

        function allNonEditable() {
            var tds = $('#rodzaje-table>tbody>tr>td');
            tds.attr('contenteditable','false');
            tds.css('background-color','white');
        }