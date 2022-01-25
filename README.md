# Maze

Maze is a visual demonstration of 3 search algorithms:

### 1. Breadth First Search
BFS is a search algorithm, in which closer places are searched before farther ones.
Read more: [Breadth-first search](https://en.wikipedia.org/wiki/Breadth-first_search)

### 2. Depth First Search
DFS is a search algorithm, in which a chosen path is chosen and searched until a dead-end is reached. Then, previous possible ways are searched.
Read more: [Depth-first search](https://en.wikipedia.org/wiki/Depth-first_search#:~:text=Depth%2Dfirst%20search%20(DFS),along%20each%20branch%20before%20backtracking.)

### 3. A-Star
A-Star uses a heuristic, to determine which places should be searched first.
Read more: [A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm)

## How to use?

1. Download the folder, run Main.java (it's inside src folder, duhhh).
2. Tell the system which maze you want to use. You can give it the name of one of the existing mazes (e.g., "maze4" or "smiley", see in the folder). You can also type "random" or leave it empty, which will lead to a randomly generated maze.
3. Tell the system which search algorithm you want: BFS, DFS or AStar. The default is AStar.

## What am I looking at, exactly?

Given an agent (in red), a goal (int blue) - does there exist a way to get from the agent to the goal?
The three algorithms share a common idea:
1. Starting from the agent, decidewhich next square you want to explore. Maybe give it a nice light-green color.
2. From all the squares you wanted to explore, choose one (which one? Depends on the specific algorithm. AStar for example chooses the one physically closest to the goal) and repeat until you find the goal.

The expanding paths you see when you run the program are the expanding search- this is where the algorithm is currently looking to find the goal.

If a path is found, the algorithm will back track, ignoring the errors it did along the way- and show you the actual best path available to it.

NOTE! The highlighted path is the guaranteed to be the shortest one only by using BFS (it is also likely to take a long time to find). Using DFS and AStar might give different results. Have fun playing with it.

NOTE 2! You can also use the maze constructor attached as an excel file.

## Example

![image](https://user-images.githubusercontent.com/45170837/115600152-a6361880-a2dc-11eb-9155-f3c2ffe3c3e0.png)
![image](https://user-images.githubusercontent.com/45170837/115600182-b1894400-a2dc-11eb-9f20-4fdbb2ec57a0.png)
