window.onload = function() {
    $.post("hello-servlet",
        {
            JansString:true
        }).always(function (data, status) {
        parseRequest(data);
    });
}

function parseRequest(data) {
    console.log("Got some data " + data)
}