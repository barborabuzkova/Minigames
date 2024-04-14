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

    const tbl = document.createElement("table");
    const tblBody = document.createElement("tbody");
    var counter = 0;
    // creating all cells
    for (let i = 0; i < SET_SIZE; i++) {
        // creates a table row
        const row = document.createElement("tr");

        for (let j = 0; j < boardSize/SET_SIZE; j++) {
            // Create a <td> element and a text node, make the text
            // node the contents of the <td>, and put the <td> at
            // the end of the table row
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
    // sets the border attribute of tbl to '2'
    tbl.setAttribute("border", "2");

    //TODO put creation of grid here instead of css based on setsize and numofchars

    // var columns = new String();
    // var rows = new String();
    // for (let i = 0; i < boardSize / SET_SIZE; i++) {
    //     columns = columns + " 140 px"
    // }
    // for (let i = 0; i < SET_SIZE; i++) {
    //     rows = rows + " 120 px"
    // }
    // console.log("rows: " + rows + " columns: " + columns)
    // document.getElementById("board").style.gridTemplateColumns = columns;
    // document.getElementById("board").style.gridTemplateRows = rows;


    //TODO stop printing to console and put it into alerts, display some information

    //create board
    // for (var i = 0; i < boardSize; i++) {
    //     const newDiv = document.createElement("div");
    //     newDiv.setAttribute("id", i)
    //     newDiv.setAttribute("class", "box")
    //     newDiv.append(data.cards[i])
    //     newDiv.addEventListener("click", cardClicked(newDiv.id.toString()))
    //     // console.log(i.toString())
    //     document.getElementById("board").appendChild(newDiv);
    // }
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