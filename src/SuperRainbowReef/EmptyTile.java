/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

/**EmptyTile Class
 * ------------------
 * Extention of entity. Mostly used as blank padding for TankWars' object2D[][]
*/

public class EmptyTile extends Entity{
   
    public EmptyTile(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
        
        empty = true;
    }
    
    public String toString() {
        return "empty";
    }

}