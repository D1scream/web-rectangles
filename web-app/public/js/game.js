let boardSize = 6;
let currentPlayer = 'w';
let playerColor = 'w';
let computerColor = 'b';
let gameBoard = [];
let gameStatus = 'running';
let winner = null;
let isPlayerTurn = true;

function init() {
    initializeBoard();
    setupEventListeners();
    renderBoard();
    updateUI();
}

function initializeBoard() {
    gameBoard = [];
    for (let i = 0; i < boardSize; i++) {
        gameBoard[i] = [];
        for (let j = 0; j < boardSize; j++) {
            gameBoard[i][j] = '.';
        }
    }
}

function setupEventListeners() {
    document.getElementById('new-game-btn').onclick = newGame;
    document.getElementById('white-color-btn').onclick = () => selectColor('w');
    document.getElementById('black-color-btn').onclick = () => selectColor('b');
}

function renderBoard() {
    const boardElement = document.getElementById('game-board');
    boardElement.innerHTML = '';
    boardElement.style.gridTemplateColumns = `repeat(${boardSize}, 1fr)`;
    
    for (let i = 0; i < boardSize; i++) {
        for (let j = 0; j < boardSize; j++) {
            const cell = document.createElement('button');
            cell.className = 'cell';
            if (gameBoard[i][j] !== '.') {
                cell.classList.add(gameBoard[i][j] === 'w' ? 'white' : 'black');
                cell.textContent = gameBoard[i][j] === 'w' ? '○' : '●';
            }
            cell.onclick = () => handleCellClick(i, j);
            boardElement.appendChild(cell);
        }
    }
}

function selectColor(color) {
    playerColor = color;
    computerColor = color === 'w' ? 'b' : 'w';
    
    document.getElementById('white-color-btn').classList.toggle('active', color === 'w');
    document.getElementById('black-color-btn').classList.toggle('active', color === 'b');
    
    newGame();
}

function handleCellClick(row, col) {
    if (!isPlayerTurn || gameStatus !== 'running' || gameBoard[row][col] !== '.') return;
    
    gameBoard[row][col] = playerColor;
    renderBoard();
    
    currentPlayer = computerColor;
    isPlayerTurn = false;
    updateUI();
    
    setTimeout(makeComputerMove, 500);
}

async function makeComputerMove() {
    if (gameStatus !== 'running') return;
    
    try {
        const boardData = getBoardString();
        const response = await fetch('http://localhost:8080/api/move', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                size: boardSize,
                data: boardData,
                nextPlayerColor: currentPlayer
            })
        });
        
        const result = await response.json();
        gameStatus = result.gameStatus;
        winner = result.winner;
        
        if (result.move) {
            gameBoard[result.move.x][result.move.y] = computerColor;
            renderBoard();
            
            if (gameStatus === 'running') {
                currentPlayer = playerColor;
                isPlayerTurn = true;
            }
        }
        
        updateUI();
    } catch (error) {
        console.error('Ошибка:', error);
        isPlayerTurn = true;
        updateUI();
    }
}

function getBoardString() {
    let result = '';
    for (let i = 0; i < boardSize; i++) {
        for (let j = 0; j < boardSize; j++) {
            result += gameBoard[i][j];
        }
    }
    return result;
}

function updateUI() {
    const currentPlayerElement = document.getElementById('current-player-color');
    const gameStatusElement = document.getElementById('game-status');
    const winMessageElement = document.getElementById('win-message');
    const gameBoardElement = document.getElementById('game-board');
    
    if (gameStatus === 'running') {
        winMessageElement.classList.add('hidden');
        winMessageElement.classList.remove('player-win', 'computer-win');
        gameBoardElement.classList.remove('computer-win');
        currentPlayerElement.textContent = currentPlayer === 'w' ? 'Белый' : 'Черный';
        gameStatusElement.textContent = isPlayerTurn ? 'Ваш ход' : 'Ход компьютера...';
    } else {
        currentPlayerElement.textContent = 'Игра завершена';
        if (gameStatus === 'white_win' || gameStatus === 'black_win') {
            const isPlayerWin = (gameStatus === 'white_win' && playerColor === 'w') || 
                               (gameStatus === 'black_win' && playerColor === 'b');
            gameStatusElement.textContent = isPlayerWin ? 'Вы выиграли!' : 'Вы проиграли!';
            winMessageElement.textContent = isPlayerWin ? 'Вы выиграли!' : 'Вы проиграли!';
            winMessageElement.classList.remove('hidden');
            
            if (isPlayerWin) {
                winMessageElement.classList.add('player-win');
                winMessageElement.classList.remove('computer-win');
            } else {
                winMessageElement.classList.add('computer-win');
                winMessageElement.classList.remove('player-win');
                gameBoardElement.classList.add('computer-win');
            }
        } else if (gameStatus === 'draw') {
            gameStatusElement.textContent = 'Ничья!';
            winMessageElement.textContent = 'Ничья!';
            winMessageElement.classList.remove('hidden');
        }
    }
}

function newGame() {
    initializeBoard();
    currentPlayer = 'w';
    gameStatus = 'running';
    winner = null;
    isPlayerTurn = playerColor === 'w';
    renderBoard();
    updateUI();
    
    if (playerColor === 'b') {
        makeComputerMove();
    }
}

document.addEventListener('DOMContentLoaded', init);