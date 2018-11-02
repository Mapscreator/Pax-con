package project.mechanics;

import project.characters.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nils Broman
 * This class handles the in list of all the objects in the game. It calls their respective tick and collision function.
 */
public class GameObjectHandler
{
    private final List<GameObject> objects = new LinkedList<>();
    void tick(){
        for (int i = 0; i < objects.size(); i++) { //Warning on the for-loop, if I fix the warning I get an error.
            GameObject object = objects.get(i);
            object.tick();
            object.getCollisionHandler().checkCollisions(object);
        }
    }

    void addObject(GameObject object){
        objects.add(object);
    }
    public void removeObject(GameObject object){
        objects.remove(object);
    }

    public List<GameObject> getObjects() {
	return objects;
    }
}
