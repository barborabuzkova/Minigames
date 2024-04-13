var SET_SIZE; // int
var NUMBER_OF_CHARACTERISTICS; // int
var boardSize; // int
var cardsClickedSoFar = [];
var cardsClickedIDs = [];
window.onload = function() {
    console.log("hello_world");
    $.post("hello-servlet",
        {
            loadPage:true
        }).done(function (data, status) {
            createBoard(data);
    });
}

function parseRequest(data) {
    if (data.collectedSet) {
        for (let i = 0; i < SET_SIZE; i++) { // remove current cards
            document.getElementById(cardsClickedIDs[i]).remove();
        }
        for (let i = 0; i < SET_SIZE; i++) { // add new cards
            const newDiv = document.createElement("div");
            newDiv.setAttribute("id", cardsClickedIDs[i])
            newDiv.setAttribute("class", "box")
            newDiv.append(data.cards[i])
            // newDiv.style.color = 'red';
            newDiv.addEventListener("click", cardClicked(newDiv.id.toString()))
            // console.log(i.toString())
            document.getElementById("board").appendChild(newDiv);
        }
    } else {
        console.log("not a set")
    }
}

//creates board and initializes global variables
function createBoard(data) {
    //constants given by the servlet
    SET_SIZE = data.SET_SIZE;
    NUMBER_OF_CHARACTERISTICS = data.NUMBER_OF_CHARACTERISTICS;
    boardSize = data.boardSize;

    //create board
    for (var i = 0; i < boardSize; i++) {
        const newDiv = document.createElement("div");
        newDiv.setAttribute("id", i)
        newDiv.setAttribute("class", "box")
        newDiv.append(data.cards[i])
        newDiv.addEventListener("click", cardClicked(newDiv.id.toString()))
        // console.log(i.toString())
        document.getElementById("board").appendChild(newDiv);
    }
}

function cardClicked(i) {
    return function () {
        console.log("cardClicked was called, i = " + i)
        //TODO if not there already - if it is, it should give a message
        cardsClickedSoFar.push(document.getElementById(i).innerText);
        cardsClickedIDs.push(i);
        if (cardsClickedSoFar.length == SET_SIZE) {
            console.log(cardsClickedSoFar)
            var myJson = JSON.stringify(cardsClickedSoFar)
            $.post("hello-servlet", {
                setFound:true,
                cardsInSet:myJson
            }).done(function (data, status) {
                parseRequest(data)
                for (let j = 0; j < SET_SIZE; j++) {
                    cardsClickedSoFar.pop();
                    cardsClickedIDs.pop();
                }
            })

        }
    }
}