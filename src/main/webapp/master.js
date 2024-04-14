var SET_SIZE; // int
var NUMBER_OF_CHARACTERISTICS; // int
var boardSize; // int
var cardsClickedSoFar = []; // string
var cardsClickedIDs = []; // int
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
        if (data.numberOfCardsAdded > SET_SIZE) { //TODO this move to switch
            for (let i = 0; i < SET_SIZE; i++) { // replace current cards
                document.getElementById(cardsClickedIDs[i]).innerText = data.cards[i];
            }
            for (let i = SET_SIZE; i < data.numberOfCardsAdded; i++) {
                for (var j = 0, row; row = document.getElementsByTagName("table").rows[j]; j++) {
                    const cell = document.createElement("td");
                    cell.setAttribute("id", j + boardSize)
                    cell.setAttribute("class", "box")
                    cell.append(data.cards[j + SET_SIZE])
                    cell.addEventListener("click", cardClicked(cell.id.toString()))
                    row.appendChild(cell)
                }
            }
            boardSize += data.numberOfCardsAdded - SET_SIZE;
        } else if (data.numberOfCardsAdded == SET_SIZE) {
            for (let i = 0; i < SET_SIZE; i++) { // replace current cards
                document.getElementById(cardsClickedIDs[i]).innerText = data.cards[i];
            }
        } else {
            for (var i = 0, row; row = table.rows[i]; i++) {
                if (!cardsClickedIDs.includes(row.cells[boardSize/SET_SIZE].id)) {
                    let exitForLoop = false;
                    for (let j = 0; j < cardsClickedIDs.length && !exitForLoop; j++) {
                        if (document.getElementById(cardsClickedIDs[j]).innerText === cardsClickedSoFar[j]) { // if innerText hasn't changed aka the card still needs to be updated
                            document.getElementById(cardsClickedIDs[j]).innerText = row.cells[boardSize/SET_SIZE].innerText;
                            exitForLoop = true;
                        }
                    }
                }
                row.removeChild(row.cells[boardSize/SET_SIZE]);
            }
        }
    } else {
        console.log("not a set")
        alert("the cards you have selected are not a set")
    }
}

//creates board and initializes global variables
function createBoard(data) {
    //constants given by the servlet
    SET_SIZE = data.SET_SIZE;
    NUMBER_OF_CHARACTERISTICS = data.NUMBER_OF_CHARACTERISTICS;
    boardSize = data.boardSize;

    // edited, originally copied from https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Traversing_an_HTML_table_with_JavaScript_and_DOM_Interfaces
    const tbl = document.createElement("table");
    const tblBody = document.createElement("tbody");
    var counter = 0; // for the id
    // creating all cells
    for (let i = 0; i < SET_SIZE; i++) {
        // creates a table row
        const row = document.createElement("tr");

        for (let j = 0; j < boardSize/SET_SIZE; j++) {
            // Create a <td> element and a text node, make the text node the contents of the <td>,
            // and put the <td> at the end of the table row
            const cell = document.createElement("td");
            cell.setAttribute("id", counter)
            cell.setAttribute("class", "box")
            cell.append(data.cards[counter])
            cell.addEventListener("click", cardClicked(cell.id.toString()))
            //const cellText = document.createTextNode(`cell in row ${i}, column ${j}`);
            //cell.appendChild(cellText);
            row.appendChild(cell);
            counter ++;
        }

        // add the row to the end of the table body
        tblBody.appendChild(row);
    }

    // put the <tbody> in the <table>
    tbl.appendChild(tblBody);
    // appends <table> into <body>
    document.body.appendChild(tbl);
    //TODO move this into css, increase size, have padding, center table, etc
    // sets the border attribute of tbl to '2'
    tbl.setAttribute("border", "2");


    //TODO stop printing to console and put it into alerts, display some information
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