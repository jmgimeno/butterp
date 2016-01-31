$("#eval-expression").submit( function (event) {
    event.preventDefault();
    var expression = $("#expression").val();
    $.post("/eval", { "expression" : expression }, function(data) {
        var pair = $("<ul/>").addClass("list-group").prependTo("#results");
        $("<li/>").addClass("list-group-item").addClass("list-group-item-info").text(data.expression).appendTo(pair);
        if (data.result) {
            $("<li/>").addClass("list-group-item").addClass("list-group-item-success").text(data.result).appendTo(pair);
            $("#clear-btn").trigger("click");
        } else {
            $("<li/>").addClass("list-group-item").addClass("list-group-item-danger").text(data.error).appendTo(pair);
        }
        $("#expression").focus();
    }, "json");
});

$("#clear-btn").on("click", function () {
    $("#eval-expression").get(0).reset();
    $("#expression").focus();
});