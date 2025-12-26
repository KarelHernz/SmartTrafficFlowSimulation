package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonExporter {
    public void export(Map estatisticas, String endereco) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(endereco), estatisticas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}