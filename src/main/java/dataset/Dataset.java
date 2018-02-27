package dataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dataset {

    private final Map<Entry, List<String>> entries;

    private final Random random = new SecureRandom();

    public Dataset(String name) throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + name + ".txt");
        entries = parse(is);
    }

    private Map<Entry, List<String>> parse(InputStream is) throws IOException {
        Map<Entry, List<String>> parsedEntries = new EnumMap<>(Entry.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
            Entry currentEntry = null;
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentEntry = extractEntry(line);
                } else {
                    if (currentEntry != null && !line.isEmpty()) {
                        parsedEntries.computeIfAbsent(currentEntry, k -> new ArrayList<>()).add(line);
                    }
                }
                line = reader.readLine();
            }
        }

        return parsedEntries;
    }

    private Entry extractEntry(String line) {
        String entryName = line.substring(1, line.length() - 1).toUpperCase();
        return Entry.valueOf(entryName);
    }

    public String pick(Entry entry) {
        List<String> availableValues = entries.get(entry);
        return availableValues.get(random.nextInt(availableValues.size()));
    }

}
