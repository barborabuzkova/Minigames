var SET_SIZE; // int
var NUMBER_OF_CHARACTERISTICS; // int
var boardSize; // int
var cardsClickedSoFar = []; // string
var cardsClickedIDs = []; // int
window.onload = function() {
    // console.log("hello_world");
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

    createStartButton(data)
}

function printRuleParagraph() {
        const rules = document.createElement("p");
        rules.append(`Set is a pattern-recognition matching game. A set is made up of ${SET_SIZE} cards.` +
            ` Each card has ${NUMBER_OF_CHARACTERISTICS} characteristics. In order for ${SET_SIZE} cards to be a` +
            ` set, each characteristic must be either all the same or all different.`)
        rules.setAttribute("id", "rules")
        document.body.appendChild(rules);
}

function createStartButton(data) {
    const button = document.createElement("button")
    button.setAttribute("id", "startButton")
    button.textContent = "Start Game"

    button.addEventListener("click", function () {
        alert(`Welcome to the start of Set! Please refer to the rules written on the page. There `
            + `${data.numberOfSets == 1 ? 'is' : 'are'}` +
            ` currently ${data.numberOfSets} ${data.numberOfSets == 1 ? 'set' : 'sets'} on the board.`)
        document.getElementById("startButton").remove();
        createRestartButton(data)
        createHintButton(data)
        createGiveUpButton(data)
        createBoard(data);
    })
    document.body.appendChild(button);
}

function createRestartButton(data) {
    const button = document.createElement("button")
    button.setAttribute("id", "restartButton")
    button.textContent = "Restart Game"

    button.addEventListener("click", function () {
        restartGame();
    })
    document.body.appendChild(button);
}

function restartGame() {
    destroy();
    while (cardsClickedSoFar.length != 0) {
        cardsClickedSoFar.pop();
        document.getElementById(cardsClickedIDs.pop()).style.outline = "none"
    }

    $.post("hello-servlet",
        {
            restart:true,
            loadPage:true
        }).done(function (data, status) {
        doInitialization(data);
    });
}
function destroy() {
    document.getElementById("rules").remove();
    document.getElementById("board").remove();
    document.getElementById("restartButton").remove();
    document.getElementById("hintButton").remove();
    document.getElementById("giveUpButton").remove();
}

function createHintButton(data) {
    const button = document.createElement("button")
    button.setAttribute("id", "hintButton")
    button.textContent = "Hint"

    button.addEventListener("click", function () {
        $.post("hello-servlet", {
            giveHint: true
        }).done(function (data, status) {
            alert("One card that is included in one of the sets is " + data.hint)
        })
    })
    document.body.appendChild(button);
}

function createGiveUpButton(data) {
    const button = document.createElement("button")
    button.setAttribute("id", "giveUpButton")
    button.textContent = "Give Up"

    button.addEventListener("click", function () {
        $.post("hello-servlet", {
            giveUp: true
        }).done(function (data, status) {
            alert("The cards in one of the sets are " + data.answer)
        })
    })
    document.body.appendChild(button);
}

function createBoard(data) { // edited, originally copied from https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Traversing_an_HTML_table_with_JavaScript_and_DOM_Interfaces
        const tbl = document.createElement("table");
        tbl.setAttribute("id", "board")
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
                cell.append(data.cards[counter])
                addStyle(cell)
                // console.log("I called addStyle")
                cell.addEventListener("click", cardClicked(cell.id.toString()))
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

function addStyle(cell) {
    let str = "";
    let index = 1;
    for (let i = 0; i < NUMBER_OF_CHARACTERISTICS; i++) {
        str = str +  ` card` + i + `w` + cell.innerText[index]
        index += 3;
    }
    cell.setAttribute("class", str)
}

function cardClicked(i) {
    return function () {
        if (!cardsClickedIDs.includes(i)) {
            // console.log("cardClicked was called, i = " + i)
            cardsClickedSoFar.push(document.getElementById(i).innerText);
            cardsClickedIDs.push(i);
            document.getElementById(i).style.outline = "8px solid lightGray"
            if (cardsClickedSoFar.length >= SET_SIZE) {
                // console.log(cardsClickedSoFar)
                var myJson = JSON.stringify(cardsClickedSoFar)
                $.post("hello-servlet", {
                    setFound:true,
                    cardsInSet:myJson
                }).done(function (data, status) {
                    if (data.gameover == "true") {
                        alert(`Congratulations! You have finished the game, there are no more sets. Click 'OK' to play again!`)
                        restartGame();
                    } else {
                        processSetCollected(data)
                        while (cardsClickedSoFar.length != 0) {
                            document.getElementById(cardsClickedIDs.pop()).style.outline = "none"
                            cardsClickedSoFar.pop();
                        }
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
                    (data.numberOfSets == 1 ? 'is' : 'are') + ` now ${data.numberOfSets} ` +
                    (data.numberOfSets == 1 ? 'set' : 'sets') + ` on the board.`)
                break;
            default: // cardsAdded greater than SET_SIZE
                replaceAndAddCards(data);
                alert(`Set successfully collected! The cards have been replaced and more have been` +
                    ` added because there was no set. There ` + (data.numberOfSets == 1 ? 'is' : 'are') +
                    ` now ${data.numberOfSets} ${data.numberOfSets == 1 ? 'set' : 'sets'}.`)
        }

    } else {
        // console.log("not a set")
        alert("The cards you have selected are not a set, please refer to the rules and try again.")
    }
}

function removeCards(data) {
    for (let i = 0; i < SET_SIZE; i++) {
        document.getElementById(cardsClickedIDs[i]).remove();
        boardSize --;
    }

    for (let currRow = 0; currRow < SET_SIZE; currRow++) {
        if (document.getElementsByTagName("tr")[currRow].cells.length > boardSize / SET_SIZE) {
            for (let rowExploring = 0; rowExploring < SET_SIZE; rowExploring++) {
                if (document.getElementsByTagName("tr")[currRow].cells.length < boardSize / SET_SIZE) {
                    document.getElementsByTagName("tr")[rowExploring].append(document.getElementsByTagName("tr")[currRow]);
                    document.getElementsByTagName("tr")[currRow].remove();
                    break;
                }
            }
        }
    }
}

function replaceCards(data) {
        for (let i = 0; i < SET_SIZE; i++) { // replace current cards
            document.getElementById(cardsClickedIDs[i]).innerText = data.cards[i];
            addStyle(document.getElementById(cardsClickedIDs[i]))
        }
}

function replaceAndAddCards(data) {
        for (let i = 0; i < SET_SIZE; i++) { // replace current cards
            document.getElementById(cardsClickedIDs[i]).innerText = data.cards[i];
            addStyle(document.getElementById(cardsClickedIDs[i]))
        }

        let counterForIDS = 0;
        for (let currRow = 0; currRow < SET_SIZE; currRow ++) {
            for (let cardInRow = 0; cardInRow < ((data.numberOfCardsAdded - SET_SIZE) / SET_SIZE); cardInRow ++) {
                row = document.getElementsByTagName("tr")[currRow];
                const cell = document.createElement("td");
                cell.setAttribute("id", boardSize + counterForIDS)
                cell.append(data.cards[SET_SIZE + counterForIDS])
                addStyle(cell)
                cell.addEventListener("click", cardClicked(cell.id.toString()))
                row.appendChild(cell)
                counterForIDS ++;
            }
        }
        boardSize += data.numberOfCardsAdded - SET_SIZE;
}

