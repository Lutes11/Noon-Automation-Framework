package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class JSONFileManager {

    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private List<Map<String, Object>> arrayAsList;

    public JSONFileManager(String jsonFilePath) {
        try {
            String content = Files.readString(new File(jsonFilePath).toPath(), StandardCharsets.UTF_8);
            root = mapper.readTree(content);

            if (root.isArray()) {
                arrayAsList = mapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {
                });
            } else if (!root.isObject()) {
                throw new IllegalArgumentException("JSON must be an object or array at the top level: " + jsonFilePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + jsonFilePath, e);
        }
    }


    public List<Map<String, Object>> asListOfObjects() {
        if (arrayAsList == null) {
            throw new IllegalStateException("Top-level JSON is not an array.");
        }
        return arrayAsList;
    }


    public Map<String, Object> asObject() {
        if (!root.isObject()) {
            throw new IllegalStateException("Top-level JSON is not an object.");
        }
        return mapper.convertValue(root, new TypeReference<Map<String, Object>>() {
        });
    }


    public Object get(String key) {
        if (!root.isObject()) throw new IllegalStateException("Top-level JSON is not an object.");
        JsonNode n = root.get(key);
        return n == null ? null : mapper.convertValue(n, Object.class);
    }


    public List<String> getStringList(String key) {
        Object v = get(key);
        if (v == null) return Collections.emptyList();
        if (v instanceof List<?> list) {
            List<String> out = new ArrayList<>();
            for (Object o : list) out.add(String.valueOf(o));
            return out;
        }
        throw new IllegalArgumentException("Field is not a list: " + key);
    }


    public Object[][] toMatrixFromTopArray(String... keysInOrder) {
        List<Map<String, Object>> list = asListOfObjects();
        Object[][] data = new Object[list.size()][keysInOrder.length];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> row = list.get(i);
            for (int k = 0; k < keysInOrder.length; k++) {
                data[i][k] = String.valueOf(row.getOrDefault(keysInOrder[k], ""));
            }
        }
        return data;
    }


    public Object[][] toMatrixFromArrayField(String arrayField, String... keysInOrder) {
        Object value = get(arrayField);
        if (!(value instanceof List<?> list)) {
            throw new IllegalArgumentException("Field is not an array: " + arrayField);
        }
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> arr = (List<Map<String, Object>>) value;

        Object[][] data = new Object[arr.size()][keysInOrder.length];
        for (int i = 0; i < arr.size(); i++) {
            Map<String, Object> row = arr.get(i);
            for (int k = 0; k < keysInOrder.length; k++) {
                data[i][k] = String.valueOf(row.getOrDefault(keysInOrder[k], ""));
            }
        }
        return data;
    }
}
