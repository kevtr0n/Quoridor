package Players.HaydenLindquist;
 
import Interface.Coordinate;
 
public class Wall 
{ 
    private Coordinate start, end;
 
    public Wall(Coordinate start, Coordinate end) 
    {
        this.start = start;
        this.end = end;        
    }
    
    
    /**
     * 
     * @param start
     * @param end
     * @return
     */
    public boolean isVertical(Coordinate start, Coordinate end)
    {
    	if(start.getCol() == end.getCol())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * 
     * @param coord
     * @return
     */
    public boolean isWall(Coordinate coord) 
    {       
        if(start.equals(coord) || end.equals(coord)) 
        {
            return true;
        }         
        return false;         
    }
    
    /**
     * 
     * @param start
     * @param end
     * @return
     */
    public boolean isHorizontal(Coordinate start, Coordinate end)
    {
    	if(start.getRow() == end.getRow())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * 
     * @return
     */
    public Coordinate getStart() 
    {
        return start;
    }
 
    /**
     * 
     * @param start
     */
    public void setStart(Coordinate start) 
    {
        this.start = start;
    }
 
    /**
     * 
     * @return
     */
    public Coordinate getEnd() 
    {
        return end;
    }
 
    /**
     * 
     * @param end
     */
    public void setEnd(Coordinate end) 
    {
        this.end = end;
    }
}