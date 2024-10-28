/**
 * Inside this file you will use the classes and functions from rx.js
 * to add visuals to the svg element in index.html, animate them, and make them interactive.
 *
 * Study and complete the tasks in observable exercises first to get ideas.
 *
 * Course Notes showing Asteroids in FRP: https://tgdwyer.github.io/asteroids/
 *
 * You will be marked on your functional programming style
 * as well as the functionality that you implement.
 *
 * Document your code!
 */
import "./style.css";

import { fromEvent, interval, merge } from "rxjs";
import { map, filter, scan } from "rxjs/operators";

/** Constants */
type TetrisShape = number[][];

const TetrisShape = [
  [[1, 1, 1, 1]],         // line-shape
  [[1, 1], [1, 1]],       // O-shape
  [[1, 1, 1], [0, 1, 0]], // T-shape
  [[1, 1, 1], [1, 0, 0]], // L-shape
  [[1, 1, 1], [0, 0, 1]], // J-shape
  [[0, 1, 1], [1, 1, 0]], // S-shape
  [[1, 1, 0], [0, 1, 1]]  // Z-shape
];

const Viewport = {
  CANVAS_WIDTH: 200,
  CANVAS_HEIGHT: 400,
  PREVIEW_WIDTH: 160,
  PREVIEW_HEIGHT: 80,
} as const;

const Constants = {
  TICK_RATE_MS: 500,
  GRID_WIDTH: 10,
  GRID_HEIGHT: 20,
} as const;

const Block = {
  WIDTH: Viewport.CANVAS_WIDTH / Constants.GRID_WIDTH,
  HEIGHT: Viewport.CANVAS_HEIGHT / Constants.GRID_HEIGHT,
};

interface LandedShape {
  shape: number[][];
  x: number;
  y: number;
  [key: number]: any;
}

/** User input */

type Key = "KeyS" | "KeyA" | "KeyD";

type Event = "keydown" | "keyup" | "keypress";

/** Utility functions */

/** State processing */

type State = Readonly<{
  gameEnd: boolean;
}>;

const initialState: State = {
  gameEnd: false,
} as const;

/**
 * Updates the state by proceeding with one time step.
 *
 * @param s Current state
 * @returns Updated state
 */
const tick = (s: State) => s;

/** Rendering (side effects) */

/**
 * Displays a SVG element on the canvas. Brings to foreground.
 * @param elem SVG element to display
 */
const show = (elem: SVGGraphicsElement) => {
  elem.setAttribute("visibility", "visible");
  elem.parentNode!.appendChild(elem);
};

/**
 * Hides a SVG element on the canvas.
 * @param elem SVG element to hide
 */
const hide = (elem: SVGGraphicsElement) =>
  elem.setAttribute("visibility", "hidden");

/**
 * Creates an SVG element with the given properties.
 *
 * See https://developer.mozilla.org/en-US/docs/Web/SVG/Element for valid
 * element names and properties.
 *
 * @param namespace Namespace of the SVG element
 * @param name SVGElement name
 * @param props Properties to set on the SVG element
 * @returns SVG element
 */
const createSvgElement = (
  namespace: string | null,
  name: string,
  props: Record<string, string> = {}
) => {
  const elem = document.createElementNS(namespace, name) as SVGElement;
  Object.entries(props).forEach(([k, v]) => elem.setAttribute(k, v));
  return elem;
};

let score = 0; // use to store the highest score in the game

/**
 * This is the function called on page load. Your main game loop
 * should be called here.
 */
export function main() {
  let gameEnd: boolean; 
  // Canvas elements
  const svg = document.querySelector("#svgCanvas") as SVGGraphicsElement &
    HTMLElement;
  const preview = document.querySelector("#svgPreview") as SVGGraphicsElement &
    HTMLElement;
  const gameover = document.querySelector("#gameOver") as SVGGraphicsElement &
    HTMLElement;
  const container = document.querySelector("#main") as HTMLElement;

  svg.setAttribute("height", `${Viewport.CANVAS_HEIGHT}`);
  svg.setAttribute("width", `${Viewport.CANVAS_WIDTH}`);
  preview.setAttribute("height", `${Viewport.PREVIEW_HEIGHT}`);
  preview.setAttribute("width", `${Viewport.PREVIEW_WIDTH}`);
  gameover.setAttribute("visibility", "hidden");

  // Text fields
  const levelText = document.querySelector("#levelText") as HTMLElement;
  const scoreText = document.querySelector("#scoreText") as HTMLElement;
  const highScoreText = document.querySelector("#highScoreText") as HTMLElement;

  /** User input */

  const key$ = fromEvent<KeyboardEvent>(document, "keypress");

  const fromKey = (keyCode: Key) =>
    key$.pipe(filter(({ code }) => code === keyCode));

  const left$ = fromKey("KeyA");
  const right$ = fromKey("KeyD");
  const down$ = fromKey("KeyS");

  /** Observables */

  /** Determines the rate of time steps */
  const tick$ = interval(Constants.TICK_RATE_MS).pipe(
    scan((s: State) => {
      if (!s.gameEnd) {
        moveDown();
      }
      return s;
    }, initialState)
  );

   /**
   * Function to generate a random Tetris shape
   */
   function getRandomShape() {
    const randomIndex = Math.floor(Math.random() * TetrisShape.length);
    return TetrisShape[randomIndex];
  }

  let tetrisX = 0; // Initial X position
  let tetrisY = 0; // Initial Y position
  let tetrisShape = getRandomShape(); // Initial Tetris shape
  let currentShape = tetrisShape; // Current Tetris shape
  let tetrisPreview = getRandomShape(); // Random Tetris shape to preview
  let landedShapes: LandedShape[] = []; // use to store the Tetris when it reach bottom
  let lineScore = 0; // to calculate the score get in one time game
  const gameGrid = Array.from({ length: Constants.GRID_HEIGHT }, () =>
  Array(Constants.GRID_WIDTH).fill(0)
  );

  /**
   * Function to clear the Tetris canvas
   */
  function clearCanvas() {
    // Continue removing child elements until there are none left
    while (svg.firstChild) {
      svg.removeChild(svg.firstChild);
    }
  }

  /**
   * Function to update the game grid based on the current piece's position
   */
  function updateGameGrid() {
    for (let i = 0; i < currentShape.length; i++) {
      for (let j = 0; j < currentShape[i].length; j++) {
        if (currentShape[i][j] === 1) {
          const gridX = tetrisX + j;
          const gridY = tetrisY + i;

          // Update the game grid to set the position as filled
          gameGrid[gridY][gridX] = 1;
        }
      }
    }
  }

  /**
   * Function to check is the Tetris will make collisions when moving the Tetris
   */
  function checkCollisions() {
    for (let i = 0; i < currentShape.length; i++) {
      for (let j = 0; j < currentShape[i].length; j++) {
        if (currentShape[i][j] === 1) {
          const gridX = tetrisX + j;
          const gridY = tetrisY + i;

          // Check if the position is already occupied by a landed shape
          if (isOccupied(gridX, gridY)) {
            return true; // Collision happen
          }
        }
      }
    }
    return false; // No collision
  }
let clearedLines = 0; // To store the total of completed rows has been clear

/**
* This function is used to clear the complete row and increment the score
* It is take idea from https://github.com/pilotpirxie/tetris-ts
*/
function checkLines() {
  let linesToClear = [];
  for (let y = Constants.GRID_HEIGHT - 1; y >= 0; y--) {
    let blocksInRow = 0;
    for (let x = 0; x < Constants.GRID_WIDTH; x++) {
      if (isOccupied(x, y)) {
        blocksInRow++;
      }
    }
    if (blocksInRow === Constants.GRID_WIDTH) {
      linesToClear.push(y);
    }
  }

  if (linesToClear.length > 0) {
    // Calculate score based on the number of lines cleared
    const linesCleared = linesToClear.length;
    // Increase the number of row based on number of lines cleared
    clearedLines += linesCleared;

    switch (linesCleared) {
      case 1:
        lineScore += 100;
        break;
      case 2:
        lineScore += 300;
        break;
      case 3:
        lineScore += 500;
        break;
      case 4:
        lineScore += 800;
        break;
      default:
        lineScore += 1000; // Adjust as needed for more lines cleared
        break;
    }

    // Clear the full rows
    for (const line of linesToClear) {
      landedShapes = landedShapes.filter((shape) => shape.y !== line);
    }

    // Shift down the remaining shapes above the cleared rows
    for (const line of linesToClear) {
      for (let i = 0; i < landedShapes.length; i++) {
        if (landedShapes[i].y < line) {
          landedShapes[i].y++;
        }
      }
    }

    // Update the score display
    scoreText.innerText = `${lineScore}`;
    // High score will update automatically if the current score is more than the past of high score
    if(score <= lineScore){
      highScoreText.innerText = `${lineScore}`;
    }
  }
}

  /**
  * Spawn the new moveable Tetris based on the preview Tetris 
  * Then create a new preview Tetris
  */
  function spawnNewTetrisPiece() {
    // Reset the Tetris piece to its initial position
    tetrisX = Math.floor(Constants.GRID_WIDTH / 2) - 1;
    tetrisY = 0;
    tetrisShape = tetrisPreview; // new Tetris based on the preview Tetris
    currentShape = tetrisShape;
    tetrisPreview = getRandomShape(); // create a new preview Tetris
  }

  /**
  * Function is use to check the place to spawn new Tetris
  */
  function canSpawnNewTetrisPiece() {
    // Check if there's enough space at the top for a new Tetris piece
    for (let i = 0; i < tetrisPreview.length; i++) {
      for (let j = 0; j < tetrisPreview[i].length; j++) {
        if (tetrisPreview[i][j] === 1) {
          const gridX = j + Math.floor(Constants.GRID_WIDTH / 2) - Math.floor(tetrisPreview[i].length / 2);
          const gridY = i;
          
          // Check if the top area is already occupied
          if (gameGrid[gridY][gridX] === 1) {
            return false; // Fail to spawn
          }
        }
      }
    }
    return true; // Allow to spawn
  }

  
  /**
   * Renders the current state to the canvas.
   *
   * In MVC terms, this updates the View using the Model.
   */
  const render = () => {
    // Clear the canvas
    clearCanvas();
  
    // Render landed shapes
    for (const landedShape of landedShapes) {
      for (let i = 0; i < landedShape.shape.length; i++) {
        for (let j = 0; j < landedShape.shape[i].length; j++) {
          if (landedShape.shape[i][j] === 1) {
            const cube = createSvgElement(svg.namespaceURI, "rect", {
              height: `${Block.HEIGHT}`,
              width: `${Block.WIDTH}`,
              x: `${Block.WIDTH * (j + landedShape.x)}`,
              y: `${Block.HEIGHT * (i + landedShape.y)}`,
              style: "fill: red",
            });
  
            svg.appendChild(cube);
          }
        }
      }
    }
  
    // Render the current Tetris piece
    for (let i = 0; i < currentShape.length; i++) {
      for (let j = 0; j < currentShape[i].length; j++) {
        if (currentShape[i][j] === 1) {
          const cube = createSvgElement(svg.namespaceURI, "rect", {
            height: `${Block.HEIGHT}`,
            width: `${Block.WIDTH}`,
            x: `${Block.WIDTH * (j + tetrisX)}`,
            y: `${Block.HEIGHT * (i + tetrisY)}`,
            style: "fill: green",
          });
  
          svg.appendChild(cube);
        }
      }
    }
  
    // Render the preview Tetris piece
    for (let i = 0; i < tetrisPreview.length; i++) {
      for (let j = 0; j < tetrisPreview[i].length; j++) {
        if (tetrisPreview[i][j] === 1) {
          const cube = createSvgElement(preview.namespaceURI, "rect", {
            height: `${Block.HEIGHT}`,
            width: `${Block.WIDTH}`,
            x: `${Block.WIDTH * j + 15}`,
            y: `${Block.HEIGHT * i + 15}`,
            style: "fill: green",
          });
  
          preview.appendChild(cube);
        }
      }
    }

    if (gameEnd) {
      show(gameover);
    } else {
      hide(gameover);
    }
  };

 /**
  * Merge the current Tetris piece with the landed shapes and update the game grid.
  */
function mergeTetrisPiece() {
  for (let i = 0; i < currentShape.length; i++) {
    for (let j = 0; j < currentShape[i].length; j++) {
      if (currentShape[i][j] === 1) {
        const gridX = tetrisX;
        const gridY = tetrisY;

        // Update the game grid to mark this position as filled
        gameGrid[gridY][gridX] = 1;

        // Add the Tetris piece to the landed shapes
        landedShapes.push({ shape: currentShape, x: gridX, y: gridY });
      }
    }
  }
}
/**
 * Function is use to make rotation of the Tetris based on clockwise
 * which means rotate a 90 degree each time this function called
 * It is using Super rotation system
 */
function rotateClockwise() {
  const rotatedShape: TetrisShape = [];
  const numRows = currentShape.length;
  const numCols = currentShape[0].length;

  // Create a new array for the rotated shape
  for (let i = 0; i < numCols; i++) {
    rotatedShape.push(new Array(numRows).fill(0));
  }

  // Rotate the shape clockwise
  for (let i = 0; i < numRows; i++) {
    for (let j = 0; j < numCols; j++) {
      rotatedShape[j][numRows - 1 - i] = currentShape[i][j];
    }
  }

  // Check if the rotation is valid (won't cause collisions)
  if (isValidRotation(rotatedShape, tetrisX, tetrisY)) {
    currentShape = rotatedShape;
  }
}

/**
 * Check if the Tetris is able to rotate
 */
function isValidRotation(rotatedShape: TetrisShape, x: number, y: number): boolean {
  // Check if the rotated shape would collide with the walls or other landed shapes
  for (let i = 0; i < rotatedShape.length; i++) {
    for (let j = 0; j < rotatedShape[i].length; j++) {
      if (rotatedShape[i][j] === 1) {
        const gridX = x + j;
        const gridY = y + i;

        // Check for collisions with walls and landed shapes
        if (
          gridX < 0 ||
          gridX >= Constants.GRID_WIDTH ||
          gridY >= Constants.GRID_HEIGHT ||
          isOccupied(gridX, gridY)
        ) {
          return false; // Cannot rotate
        }
      }
    }
  }

  return true; // Can rotate
}

/**
 * Function to move the Tetris piece left
 */
function moveLeft() {
  if (canMoveLeft()) {
    tetrisX--;
    render();
  }
}

/**
 * Function to move the Tetris piece right
 */
function moveRight() {
  if (canMoveRight()) {
    tetrisX++;
    render();
  }
}

/**
 * Function to move the Tetris piece down
 */
function moveDown() {
  if (canMoveDown() && !checkCollisions()) {
    tetrisY++;
    render();
  } else {
    clearPreview(); //make sure the past used preview will not show in preview place
    updateGameGrid(); 
    mergeTetrisPiece();
    checkLines(); // Call checkLines after merging the Tetris piece

    // Check the current landed Tetris position
    if (!gameEnd && canSpawnNewTetrisPiece()) {
      spawnNewTetrisPiece(); // Spawn a new Tetris piece
    }else{
      gameEnd = true;
    }
      
    render();
  }
}

/**
 * Function to check if the Tetris piece can move left
 */
function canMoveLeft() {
  for (let i = 0; i < currentShape.length; i++) {
    for (let j = 0; j < currentShape[i].length; j++) {
      if (currentShape[i][j] === 1) {
        const newX = tetrisX + j - 1;
        if (newX < 0 || isOccupied(newX, tetrisY + i)) {
          return false; // Cannot move left, out of bounds or collision
        }
      }
    }
  }
  return true;
}

/**
 * Function to check if the Tetris piece can move right
 */
function canMoveRight() {
  for (let i = 0; i < currentShape.length; i++) {
    for (let j = 0; j < currentShape[i].length; j++) {
      if (currentShape[i][j] === 1) {
        const newX = tetrisX + j + 1;
        if (newX >= Constants.GRID_WIDTH || isOccupied(newX, tetrisY + i)) {
          return false; // Cannot move right, out of bounds or collision
        }
      }
    }
  }
  return true;
}

/**
 * Function to check if the Tetris piece can move down
 */
function canMoveDown() {
  for (let i = 0; i < currentShape.length; i++) {
    for (let j = 0; j < currentShape[i].length; j++) {
      if (currentShape[i][j] === 1) {
        const newY = tetrisY + i + 1;
        if (newY >= Constants.GRID_HEIGHT || isOccupied(tetrisX + j, newY)) {
          return false; // Cannot move down, out of bounds or blocked by a landed shape
        }
      }
    }
  }
  return true;
}

/**
 * Function to check if a grid position is occupied by a landed shape
 * 
 * @param x the column number within the grid
 * @param y the row number within the grid.
 */
function isOccupied(x: number, y: number) {
  for (const landedShape of landedShapes) {
    for (let i = 0; i < landedShape.shape.length; i++) {
      for (let j = 0; j < landedShape.shape[i].length; j++) {
        if (landedShape.shape[i][j] === 1) {
          const gridX = landedShape.x + j;
          const gridY = landedShape.y + i;
          if (x === gridX && y === gridY) {
            return true; // Position is occupied
          }
        }
      }
    }
  }
  return false; // Position is not occupied
}

/**
 * Make interval for automatic downward movement
 * 
 */
const autoMoveDown$ = interval(Constants.TICK_RATE_MS);

autoMoveDown$.subscribe(() => {
  let i =1;
  levelText.innerText = `${i}`// show the current difficulty level

  // Check the number of rows have been cleared
  // If the total of cleared rows is reach the specifc one
  // The difficulty level will increase and the speed of auto movement of Tetris will also increase
  if(clearedLines>=i*15){
    i++
    levelText.innerText = `${i}`
    tetrisY++
  }
  moveDown();
});

/**
 * Function to clear the old preview Tetris
 */
function clearPreview() {
  while (preview.firstChild) {
    preview.removeChild(preview.firstChild);
  }
}

/**
 * Function to restart the game
 */
function restartGame() {
  // Reset game state variables to their initial values
  tetrisX = 0;
  tetrisY = 0;
  tetrisShape = getRandomShape();
  currentShape = tetrisShape;
  tetrisPreview = getRandomShape();
  landedShapes = [];
  gameGrid.forEach((row) => row.fill(0));
  gameEnd = false;
  clearPreview();

  //Update the hightscore
  if(score < lineScore){
    highScoreText.innerText = `${lineScore}`;
    lineScore = 0;
    scoreText.innerText = `${lineScore}`;
  }else{
    lineScore = 0;
    scoreText.innerText = `${lineScore}`
  }
  // hide game over screen if it's visible
  hide(gameover);
}

/**
 * Add event listeners
 */
document.addEventListener("keydown", (event) => {
  if (event.key === "ArrowLeft") {
    moveLeft();
  }else if (event.key === "ArrowRight") {
    moveRight();
  }else if(event.key === "ArrowDown"){
    moveDown();
  }else if (event.key === " ") {
    // Restart the game when the space key is pressed
    restartGame();
  } else if (event.key === "ArrowUp") {
    // Rotate the Tetris piece clockwise when the "ArrowUp" key is pressed
    rotateClockwise();
    render();
  }
});


  

  const source$ = merge(tick$)
    .pipe(scan((s: State) => ({ gameEnd: false }), initialState))
    .subscribe((s: State) => {
      render();

      if (s.gameEnd) {
        show(gameover);
      } else {
        hide(gameover);
      }
    });

    
}



// The following simply runs your main function on window load.  Make sure to leave it in place.
if (typeof window !== "undefined") {
  window.onload = () => {
    main();
  };
}
