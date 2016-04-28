package klabbristol.holidayservice.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(
            JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        return LocalDate.parse(json.getAsString());
    }

    @Override
    public JsonElement serialize(
            LocalDate src, Type typeOfSrc, JsonSerializationContext context
    ) {
        return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE.format(src));
    }
}
