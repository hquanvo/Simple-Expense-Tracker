package persistence;

import org.json.JSONObject;

// Modeled after the interface with the same name in JsonSerializationDemo project provided by CPSC 210 instructors
// at UBC
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
