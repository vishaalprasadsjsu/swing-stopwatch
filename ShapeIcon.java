import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
   An icon that contains a moveable shape.
*/
public class ShapeIcon implements Icon
{
   /**
    * Creates a new ShapeIcon with the given parameters
    * @param shape a MoveableShape to be contained in this ShapeIcon
    * @param width
    * @param height
    */
   public ShapeIcon(MoveableShape shape,
      int width, int height)
   {
      this.shape = shape;
      this.width = width;
      this.height = height;
   }

   /**
    * @return this ShapeIcon's width
    */
   public int getIconWidth()
   {
      return width;
   }

   /**
    * @return this ShapeIcon's height
    */
   public int getIconHeight()
   {
      return height;
   }

   /**
    * Paint this ShapeIcon with the given parameters
    * @param c Component for painting
    * @param g Graphics for painting
    * @param x X-coordinate in which to paint this ShapeIcon
    * @param y Y-coordinate in which to paint this ShapeIcon
    */
   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      Graphics2D g2 = (Graphics2D) g;
      shape.draw(g2);
   }

   private int width;
   private int height;
   private MoveableShape shape;
}


