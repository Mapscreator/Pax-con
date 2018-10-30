package project.mechanics;

/**
 * Created by Nils Broman
 */
public enum Level
{

    /**
     * Level one
     */

    LEVEL1(1,0,0), // 2, 0, 0

    /**
     * Level two
     */

    LEVEL2(6,0,0),

    /**
     * Level three
     */

    LEVEL3(6,1,0),

    /**
     * Level four
     */

    LEVEL4(4,0,0),

    /**
     * Level five
     */

    LEVEL5(3,3,0),

    /**
     * Level six
     */

    LEVEL6(1,6,0),

    /**
     * Level seven
     */

    LEVEL7(8,0,0),

    /**
     * Level eight
     */

    LEVEL8(3,4,2),

    /**
     * Level nine
     */

    LEVEL9(5,4,2),

    /**
     * Level ten
     */

    LEVEL10(10,10,4);

    private final int basicEnemy;
    private final int largeEnemy;

    Level(int basicEnemy, int largeEnemy, int blueEnemy){
        this.basicEnemy = basicEnemy;
        this.largeEnemy = largeEnemy;
    }

    public int getBasicEnemy(){
        return basicEnemy;
    }
    public int getLargeEnemy(){
        return largeEnemy;
    }
}
