package Players.HaydenLindquist;
 
import Interface.Coordinate;

/**
 * 
 * @author Kevin
 *
 */
public class Pawn 
{
    private int id;
    private int walls;
    private Coordinate location;
    
    /**
     * 
     * @param id
     * @param walls
     * @param location
     */
    public Pawn(int id, int walls, Coordinate location) 
    {
         
        this.id = id;
        this.walls = walls;
        this.location = location;
    }
    
    /**
     * 
     * @return
     */
    public int getId() 
    {
        return id;
    }
 
    /**
     * 
     * @param id
     */
    public void setId(int id) 
    {
        this.id = id;
    }
    
    /**
     * 
     * @return
     */
    public int getWalls() 
    {
        return walls;
    }
 
    /**
     * 
     * @param walls
     */
    public void setWalls(int walls) 
    {
        this.walls = walls;
    }
    
    /**
     * 
     * @return
     */
    public Coordinate getLocation() 
    {
        return location;
    }
    
    /**
     * 
     * @param location
     */
    public void setLocation(Coordinate location) 
    {
        this.location = location;
    }
}