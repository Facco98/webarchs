const BASE_GAME_URL = 'game/';

function updateLabel(querySelector, title, value){
    const attemptsLeft = document.querySelector(querySelector);
    attemptsLeft.innerHTML = '';
    const p = document.createElement('h2');
    p.innerText = title + ' '+ value;
    attemptsLeft.appendChild(p);
}

async function onCardClick(rowIndex, colIndex){

    const body = {rowIndex: rowIndex, colIndex: colIndex};
    let result = await fetch(BASE_GAME_URL, {
        method: 'PUT',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(body)
    });
    const gameState = await result.json();
    const finished = gameState.attempts <= 0;
    const wrongPairDiscovered = false;
    updateLabel('#attempts-left', 'Attempts left:', gameState.attempts);
    updateLabel('#score', 'Score:', gameState.score);
    updateField('#field', gameState.values, !finished && !wrongPairDiscovered);

    let timeoutCallback = loadGame;
    if( finished ){
        updateLabel('#attempts-left', '', 'GAME OVER')
        timeoutCallback = () => window.location.href='.';

    }
    console.log(timeoutCallback)
    window.setTimeout(timeoutCallback, 1000)

}

function updateField(querySelector, field, makeClickable){

    const fieldElem = document.querySelector(querySelector);
    fieldElem.innerHTML = '';
    const table = document.createElement('table');
    for( let i = 0; i < field.length; i++ ){
        const row = field[i];
        const tableRow = document.createElement('tr');
        for(  let j = 0; j < row.length; j++  ){
            const val = row[j];
            const cell = document.createElement('td');
            const img = document.createElement('img');
            const isFaceUp = val !== -1;
            const imgUrl = isFaceUp ? 'number-' + val : 'cardBack';

            img.setAttribute('src', '../img/' + imgUrl + '.jpg');
            if( !isFaceUp && makeClickable ) {
                img.classList.add('clickable');
                img.addEventListener('click', _ => {
                   onCardClick(i,j);
                });
            }

            cell.appendChild(img);
            tableRow.appendChild(cell);
        }
        table.appendChild(tableRow);
    }

    fieldElem.appendChild(table);



}

function loadGame(){


    fetch(BASE_GAME_URL).then(resp => resp.json()).then(game => {

        updateLabel('#attempts-left', 'Attempts left:', game.attempts);
        updateLabel('#score', 'Score:', game.score);
        updateField('#field', game.values, !game.failed);
    }).catch(error => {
        console.error(error);
        alert('Error loading the game!');
    });


}

loadGame();