package Players.HaydenLindquist;

import java.util.*;

import Engine.Logger;
import Interface.Coordinate;
import Interface.PlayerModule;
import Interface.PlayerMove;

public class HaydenLindquist implements PlayerModule {
    
	Coordinate newCoords;
	Wall theWall;
    private Logger logOut;
    Pawn player;
    Pawn opponent;
    List<Wall> wallList;
    List<Pawn> pawnList;
    
    /**
     * Getter method that retrieves a Pawn object's playerID.
     * 
     * @return				Returns an int playerId.
     */
    public int getID() 
    {
        return player.getId();
    }
 
    /**
     * TODO - This method is tripping up when walls are added to the board via the PRE_MOVES
     * in the config.txt doc. I think it is because it does not account for the orientation 
     * of the wall. Due to walls being in-between tiles you are not allowed to cross a wall
     * but you ARE allowed to move in plane with one. If wall class had some identifier for 
     * wall orientation or this class had a method that could check it we could compare it with
     * the coordinate given in this method, i.e. a horizontal wall to the right of the
     * coordinate would not impede movement whereas a vertical one would.
     * 
     * @return				Returns a Set<Coordinate> neighbor.
     */
    public Set<Coordinate> getNeighbors(Coordinate c) {
         
        // Creates HashSet we will use to store neighbor tiles
        Set<Coordinate> neighbor = new HashSet<Coordinate>();
         
        int x = c.getRow();
        int y = c.getCol();
         
        // Coordinates for the 4 adjacent spaces
        Coordinate top = new Coordinate(x,y-1);
        Coordinate bottom = new Coordinate(x,y+1);
        Coordinate left = new Coordinate(x-1,y);
        Coordinate right = new Coordinate(x+1,y);
         
        // Following if statements will check if the provided coordinate is in a corner
        // or along an edge of the board. Then the appropriate adjacent squares will be 
        // checked for walls and if there are none the coordinate will be added to the HashSet.
        if(x == 0) {
            if(y == 0) {
                if(! wallCheck(right))
                    neighbor.add(right);
                if(! wallCheck(bottom))
                    neighbor.add(bottom);
            }
            else if(y == 8) {
                if(! wallCheck(top))
                    neighbor.add(top);
                if(! wallCheck(right))
                    neighbor.add(right);
            }
            else {
                if(! wallCheck(top))
                    neighbor.add(top);
                if(! wallCheck(right))
                    neighbor.add(right);
                if(! wallCheck(bottom))
                    neighbor.add(bottom);
            }
        }
         
        else if(x == 8) {
            if(y == 0) {
                if(! wallCheck(left))
                    neighbor.add(left);
                if(! wallCheck(bottom))
                    neighbor.add(bottom);
            }
            else if(y == 8) {
                if(! wallCheck(top))
                    neighbor.add(top);
                if(! wallCheck(left))
                    neighbor.add(left);
            }
            else {
                if(! wallCheck(top))
                    neighbor.add(top);
                if(! wallCheck(left))
                    neighbor.add(left);
                if(! wallCheck(bottom))
                    neighbor.add(bottom);
            }
        }
         
        else if(y == 0) {
            if(! wallCheck(right))
                neighbor.add(right);
            if(! wallCheck(left))
                neighbor.add(left);
            if(! wallCheck(bottom))
                neighbor.add(bottom);
        }
         
        else if(y == 8) {
            if(! wallCheck(right))
                neighbor.add(right);
            if(! wallCheck(left))
                neighbor.add(left);
            if(! wallCheck(top))
                neighbor.add(top);
        }
         
        else {
            if(! wallCheck(right))
                neighbor.add(right);
            if(! wallCheck(left))
                neighbor.add(left);
            if(! wallCheck(top))
                neighbor.add(top);
            if(! wallCheck(bottom))
                neighbor.add(bottom);
        }      
        return neighbor;         
    } 
    
    /**
     * Getter method that retrieves a specified player's Pawn
     * location.
     * 
     * @return					Returns Pawn Coordinates.
     */
    public Coordinate getPlayerLocation(int playerID) 
    {         
        if(playerID == player.getId()) 
        {
            return(player.getLocation());
        }
        else return(opponent.getLocation());         
    }
    
    /**
     * Creates a map that entails the location of each player's
     * Pawn locations.
     * 
     * @return					Returns Map object
     */
    public Map<Integer, Coordinate> getPlayerLocations() 
    {
         
        // Creates HashMap of Integer, Coordinate type
        HashMap<Integer, Coordinate> locations = new HashMap<Integer, Coordinate>();
         
        // Adds the ID and locations of the 2 players to the HashMap
        locations.put(player.getId(), player.getLocation());
        locations.put(opponent.getId(), opponent.getLocation());    
         
        return locations;
    }
 
    /**
     * Method that returns a list of Coordinates which acts as a path
     * when given a Map parameter and two Coordinate parameters
     * start and finish.
     * 
     * @param newRoute			Map parameter.
     * @param start				Coordinate parameter.
     * @param finish			Coordinate parameter.
     * @return					Returns a List<Coordinate> route.
     */
    private List<Coordinate> createRoute (Map<Coordinate, Coordinate> newRoute, Coordinate start, Coordinate finish)
    {
    	List <Coordinate> route = new LinkedList<Coordinate>();
    	
    	if(newRoute.containsKey(finish))
    	{
    		Coordinate location = finish;
    		while(!(location == start))
    		{
    			route.add(0, location);
    			location = newRoute.get(location);
    		}
    		route.add(0, start);
    	}
    	return route;
    }
     
    /**
     * This method compiles a list of coordinates that detail the
     * shortest route from the Coordinate start to the Coordinate
     * end.
     * 
     * @param					Coordinate start
     * @param					Coordinate end
     * @return					Returns a List<Coordinate>
     */
    public List<Coordinate> getShortestPath(Coordinate start, Coordinate end) 
    {
    	// Creates a Coordinate list for the shortest path
    	List<Coordinate> path = new LinkedList<Coordinate>();
    	path.add(start);
    	
    	// New representation of parameters.
    	Coordinate a = start; Coordinate b = end;
    	
    	Map<Coordinate, Coordinate> board = new HashMap<Coordinate, Coordinate>();
    	
    	// While loop for a non-empty list.
    	while(!path.isEmpty())
    	{
    		Coordinate spot = path.remove(0);
    		if(spot == end)
    		{
    			break;
    		}
    		
    		for(Iterator<Coordinate> scan = getNeighbors(spot).iterator(); scan.hasNext();)
    		{
    			Coordinate adjacent = scan.next();
    			if(!board.containsKey(adjacent))
    			{
    				board.put(adjacent, spot);
    				path.add(adjacent);
    			}
    		}
    	}
        return createRoute(board, a, b);
    }
     
    /**
     * Getter method that retrieves the number of remaining
     * walls a specified player is able to place.
     * 
     * @param					int playerID
     * @return					Returns int number of walls.
     */
    public int getWallsRemaining(int playerID) 
    {         
        if(playerID == player.getId()) 
        {
            return(player.getWalls());
        }
        else return(opponent.getWalls());
    }
 
    /**
     * This class's version of a constructor method.
     * 
     * @param					Logger logger
     * @param					int playerID
     * @param					int numWalls
     * @param					Map<Integer, Coordinate> playerHomes
     */
    public void init(Logger logger, int playerID, int numWalls, Map<Integer, Coordinate> playerHomes) 
    {         
        logOut = logger;         
        // Creates ArrayList used to store wall objects
        wallList = new ArrayList<Wall>();         
        // Creates our two players and initializes them with data from engine
        for ( Integer i : (Set<Integer>) playerHomes.keySet() ) 
        {
            if ( i == playerID )
                player = new Pawn(playerID,numWalls,playerHomes.get(i));
            else 
            {
                opponent = new Pawn(2,numWalls,playerHomes.get(i));
            }
        }   
    }
 
     
    /**
     * 
     * 
     * @param					PlayerMove m
     */
    public void lastMove(PlayerMove m) 
    {         
        // Check if m is a player move or wall placement
        if(m.isMove()) 
        {            
            // Switch to differentiate between player 1 and 2.
            // then updates the appropriate players location
            switch(m.getPlayerId()) 
            {             
            case 1:
                player.setLocation(m.getEnd());
                break;
             
            case 2:
                opponent.setLocation(m.getEnd());
                break;
            }   
        }         
        else 
        {             
//          Coordinate newEnd;
//          
//          // Corrects for PRE_MOVE sending coordinates 3 units apart for walls by
//          // subtracting 1 from the walls x/y end coordinate.
//          if(m.getStartRow() == m.getEndRow())
//              newEnd = new Coordinate(m.getEndRow() , (m.getEndCol() - 1));
//          else newEnd = new Coordinate((m.getEndRow() - 1) , m.getEndCol());
             
            // Checks which player placed the wall. Then records the wall placement using
            // addWall() and decrements the corresponding players wall count.
            switch(m.getPlayerId()) 
            {            
            case 1:
                addWall(m.getStart(), m.getEnd());
                player.setWalls(player.getWalls() - 1);
                break;
             
            case 2:
                addWall(m.getStart(), m.getEnd());
                opponent.setWalls(player.getWalls() - 1);
                break;
            }       
        }
    }
 
     
    /**
     * 
     * @return					Returns a Set<PlayerMove>
     */
    public Set<PlayerMove> allPossibleMoves() 
    {
        return null;
    }
 
     
    /**
     * 
     * 
     */
    public PlayerMove move() 
    {
        return null;
    }
    
    /**
     * 
     * @param					int playerID
     */
    public void playerInvalidated(int playerID) 
    {
         
    }
          
    /**
     * Method that creates a new wall object and adds it to the wallList ArrayList.
     * 
     * @param 					Coordinate start
     * @param 					Coordinate end
     */
    public void addWall(Coordinate start, Coordinate end) 
    {
        Wall w = new Wall(start,end);
        wallList.add(w);                 
    }
    
    /**
     * A check method to see if entered coordinate contains a section of a wall.
     * 
     * @param 					Coordinate c
     * @return					Returns a boolean value based on wall presence.
     */
    public boolean wallCheck(Coordinate c) 
    {
        // Iterates through wall objects in wallList
        for(int i = 0; i < wallList.size(); i++) 
        {            
            // Check if any adjacent squares contain a section of a wall
            if(wallList.get(i).isWall(c)) 
            {
                return true;
            }
        }         
        return false;         
    }   
}