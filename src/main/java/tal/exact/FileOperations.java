package tal.exact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class FileOperations {
    public static String getJsonStringFromFileInResources(String fileName) {
        InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName);
        try {
            byte[] bytes = Objects.requireNonNull(is).readAllBytes();
            return new String(bytes);
        } catch (Exception e) {
            System.out.println("ERR! Unable to read file");
            return "";
        }
    }

    public static List<Edge> getEdgesListFromJsonString(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            System.out.println("ERR! Unable to parse json");
            return List.of();
        }
    }
}
