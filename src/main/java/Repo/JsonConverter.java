package Repo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class JsonConverter
{
    public static AddonJson[] convertToAddons(File json_file) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json_file, AddonJson[].class);
    }

    public static FilterJson[] converToFilters(File json_file) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json_file, FilterJson[].class);
    }
}
