package project.mechanics;

/**
 * Created by Nils Broman
 */
public enum Level
{

    /**
     * Level one
     */

    LEVEL1(2,0),

    /**
     * Level two
     */

    LEVEL2(6,0),

    /**
     * Level three
     */

    LEVEL3(6,1),

    /**
     * Level four
     */

    LEVEL4(4,3),

    /**
     * Level five
     */

    LEVEL5(7,3),

    /**
     * Level six
     */

    LEVEL6(1,6),

    /**
     * Level seven
     */

    LEVEL7(8,0),

    /**
     * Level eight
     */

    LEVEL8(3,4),

    /**
     * Level nine
     */

    LEVEL9(5,4),

    /**
     * Level ten
     */

    LEVEL10(10,10);

    private final int basicEnemy;
    private final int largeEnemy;

    Level(int basicEnemy, int largeEnemy){
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
