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
            doInitialization(data);
    });
}

//creates board and initializes global variables
function doInitialization(data) {
    //constants given by the servlet
    SET_SIZE = data.SET_SIZE;
    NUMBER_OF_CHARACTERISTICS = data.NUMBER_OF_CHARACTERISTICS;
    boardSize = data.boardSize;

    printRuleParagraph();

    createBoard(data);

    alert(`Welcome to the start of Set! Please refer to the rules written on the page. There `
        + `${data.numberOfSets == 1 ? 'is' : 'are'}` +
        ` currently ${data.numberOfSets} ${data.numberOfSets == 1 ? 'set' : 'sets'} on the board.`)
}

function printRuleParagraph() {
        const rules = document.createElement("p");
        rules.append(`Set is a pattern-recognition matching game. A standard set is made up of ${SET_SIZE} cards.` +
            ` Each card has ${NUMBER_OF_CHARACTERISTICS} characteristics. In order for ${SET_SIZE} cards to be a` +
            ` set, each characteristic must be either all the same or all different.`)
        document.body.appendChild(rules);
}

function createBoard(data) { // edited, originally copied from https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Traversing_an_HTML_table_with_JavaScript_and_DOM_Interfaces
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
}

function cardClicked(i) {
    return function () {
        if (!cardsClickedIDs.includes(i)) {
            console.log("cardClicked was called, i = " + i)
            cardsClickedSoFar.push(document.getElementById(i).innerText);
            cardsClickedIDs.push(i);
            if (cardsClickedSoFar.length == SET_SIZE) {
                console.log(cardsClickedSoFar)
                var myJson = JSON.stringify(cardsClickedSoFar)
                $.post("hello-servlet", {
                    setFound:true,
                    cardsInSet:myJson
                }).done(function (data, status) {
                    processSetCollected(data)
                    for (let j = 0; j < SET_SIZE; j++) {
                        cardsClickedSoFar.pop();
                        cardsClickedIDs.pop();
                    }
                })
            }
        } else {
            alert("card already selected, please choose a different card")
        }
    }
}

function processSetCollected(data) {
    if (data.collectedSet) {
        switch (data.numberOfCardsAdded - SET_SIZE) {
            case -SET_SIZE: //cardsAdded equals zero
                removeCards(data);

                alert(`Set successfully collected! No new cards have been added because ` +
                    (data.numberOfSets + data.numberOfSets == 1 ? 'set is' : 'sets are') +
                    ` still on the board.`)
                
                break;
            case 0: // cardsAdded equals SET_SIZE
                replaceCards(data);
                alert(`Set successfully collected! The cards have been replaced. There ` +
                    (data.numberOfSets == 1 ? 'is' : 'are') + `now ${data.numberOfSets} ` +
                    (data.numberOfSets == 1 ? 'set' : 'sets') + ` on the board.`)
                break;
            default: // cardsAdded greater than SET_SIZE
                replaceAndAddCards(data);
                alert(`Set successfully collected! The cards have been replaced and more have been` +
                    ` added because there was no set. There ` + (data.numberOfSets == 1 ? 'is' : 'are') +
                    ` now ${data.numberOfSets} ${data.numberOfSets == 1 ? 'set' : 'sets'}.`)
        }

    } else {
        //console.log("not a set")
        alert("The cards you have selected are not a set, please refer to the rules and try again.")
    }
}

function removeCards(data) {
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

function replaceCards(data) {
        for (let i = 0; i < SET_SIZE; i++) { // replace current cards
            document.getElementById(cardsClickedIDs[i]).innerText = data.cards[i];
        }
}

function replaceAndAddCards(data) {
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
}