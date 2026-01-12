package util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;

public class JsonExporter {
    public void export(Statistics statistics, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //Ativa a indentação para que os dados que estão dentro do .json estejam indentados
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            //Cria o ficheiro estadisticas.json no diretorio selecionado
            File file = new File(path, "estadisticas.json");
            mapper.writeValue(file, statistics);
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}