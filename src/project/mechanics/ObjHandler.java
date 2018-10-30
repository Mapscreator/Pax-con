package project.mechanics;

import project.characters.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nils Broman
 */
public class ObjHandler {

    private final List<GameObject> objects = new LinkedList<>();
    void tick(){
        for (int i = 0; i < objects.size(); i++) { //Varning på for-loopen, fixar jag varningen får jag error istället.
            GameObject object = objects.get(i);
            object.tick();
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
