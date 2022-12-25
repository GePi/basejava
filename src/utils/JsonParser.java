package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.AbstractSection;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {

    private final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<AbstractSection>())
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
            .create();

    public <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public <T> void write(T obj, Writer writer) {
        GSON.toJson(obj, writer);
    }
}
