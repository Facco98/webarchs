function loadRanking(){
    const url = 'me/ranking';
    const rankingDiv = document.querySelector("#ranking-list");

    fetch(url).then(resp => resp.json()).then(rankingBean => {

        rankingDiv.innerHTML = '';
        let result = undefined;

        if( rankingBean.entries.length === 0){
            result = document.createElement('p');
            result.innerHTML = 'No game played yet!';
        } else {

            result = document.createElement('ul');
            for( let rankingEntry of rankingBean.entries ){
                const li = document.createElement('li');
                li.innerHTML = rankingEntry.username + ' - ' + rankingEntry.points;
                result.appendChild(li);
            }

        }
        rankingDiv.appendChild(result);

    }).catch(err => {
        console.error(err);
        rankingDiv.innerHTML='Error loading the ranking';
    });

}

window.setInterval(loadRanking, 1000);

