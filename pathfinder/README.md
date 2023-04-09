# Assignment A4: Urbanism

- Author: Hamza Abou Jaib 
- Email: aboujaih@mcmaster.ca

## Path Finder Library

### Explanation of pathfinder library

#### Graph ADT
The Graph ADT is a data structure that uses the adjacently list representation and consists of nodes connected by edges. 
The node data type can be created with any number and type of attributes. 
The edge data type represents a relation between two nodes. The edges are directed.
The edge can be constructed without weight, giving them a weight of 1, or constructed with a given weight.

#### Util
The util package includes some useful utilities such as a minimum priority
queue, a way to store pairs and dijkstra's algorithm. The minimum priority queue
can be used for any purpose, however in the pathfinder library it is used as part of Dijkstra's algorithm.
The Pair class is used to allow two returns from a function and is used to return the path and cost from Dijkstra's algorithm.
Finally, Dijkstra class is used to run Dijkstra's algorithm and obtain the shortestPath.

#### PathFinder and ShortestPath
The pathfinder library provides an interface called PathFinder such that anyone
can implement it and create their own path finding algorithm. In the pathfinder library a
default pathfinding algorithm that finds the shortest path between two nodes is provided. 
The shortest path algorithm returns a list containing the starting node, the ending node and every other node between them in the path.

