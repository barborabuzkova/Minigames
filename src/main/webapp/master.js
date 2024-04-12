var SET_SIZE; //int
var NUMBER_OF_CHARACTERISTICS; //int
var boardSize; //int
var cardsClickedSoFar = [];
window.onload = function() {
    console.log("hello_world");
    $.post("hello-servlet",
        {
            loadPage:true
        }).done(function (data, status) {
            createBoard(data);
            //parseRequest(data);
            for (let i = 0; i < boardSize; i++) {
                console.log("hello")
                document.getElementById(i.toString()).addEventListener("click", cardClicked(i.toString()));
            }
    });
    console.log("hi")

    console.log("hihihi")
}

function parseRequest(data) {
    window._data = data;
    //console.log("Got some data " + window._data)
    //console.log(window._data.card1)
}

//creates board and initializes global variables
function createBoard(data) {
    //constants given by the servlet
    SET_SIZE = data.SET_SIZE;
    NUMBER_OF_CHARACTERISTICS = data.NUMBER_OF_CHARACTERISTICS;
    boardSize = data.boardSize;

    //create board
    for (let i = 0; i < boardSize; i++) {
        const newDiv = document.createElement("div");
        newDiv.setAttribute("id", i)
        newDiv.setAttribute("class", "box")
        newDiv.append(data.cards[i])
        // document.body.insertBefore(newDiv, document.getElementById("board"))
        document.getElementById("board").appendChild(newDiv);
    }
}

function cardClicked(i) {
    return function () {
        console.log("cardClicked was called, i = " + i.innerText)
        cardsClickedSoFar.push(document.getElementById(i));
        console.log("hi " + cardsClickedSoFar)
    }
}