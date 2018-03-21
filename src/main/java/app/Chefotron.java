package app;

import dataset.Dataset;
import dataset.Entry;
import java.io.IOException;

public class Chefotron {

    public static void main(String... args) throws IOException {
        Dataset dataset = new Dataset("formal");
        String result = generateMail(dataset);
        System.out.println(result);
    }

    private static String generateMail(Dataset dataset) {
        return dataset.pick(Entry.INTRODUCTION) +
                "\n" +
                merge(merge(dataset.pick(Entry.REASON), dataset.pick(Entry.LEAD)), dataset.pick(Entry.MAIN)) +
                ".\n\n" +
                dataset.pick(Entry.FOLLOWUP) +
                ".\n" +
                dataset.pick(Entry.SALUTATION);
    }

    private static String merge(String lead, String main) {
        // Transforms "de" to "d'" when right before a vowel
        if (lead.endsWith(" de") && main.matches("^[aeiouyéêèà].*")) {
            lead = lead.substring(0, lead.length() - 1) + "'";
        } else {
            lead = lead + " ";
        }
        return lead + main;
    }

}
