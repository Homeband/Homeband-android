package be.heh.homeband.app;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicolas on 26-01-18.
 */

public class HomebandApiReponse extends HashMap<String, JsonElement> {
    
    private boolean operationReussie;
    private String message;

    public HomebandApiReponse(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.mapResultat();
    }

    public HomebandApiReponse(int initialCapacity) {
        super(initialCapacity);
        this.mapResultat();
    }

    public HomebandApiReponse() {
        super();
        this.mapResultat();
    }

    public HomebandApiReponse(Map<? extends String, ? extends JsonElement> map) {
        super(map);
        this.mapResultat();
    }

    public boolean isOperationReussie() {
        return operationReussie;
    }

    public String getMessage() {
        return message;
    }

    public void mapResultat(){
        if(this.containsKey("status")) {
            this.operationReussie = this.get("status").getAsBoolean();
        }

        if(this.containsKey("message")) {
            this.message = this.get("message").getAsString();
        }
    }
}
