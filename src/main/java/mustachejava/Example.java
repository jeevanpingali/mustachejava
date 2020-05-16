package mustachejava;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;

public class Example {

    List<Item> items() {
        return Arrays.asList(
                new Item("Item 1", "$19.99", Arrays.asList(new Feature("New!"), new Feature("Awesome!")),
                        Arrays.asList(new Feature("New First Feature"), new Feature("New Second Feature"))
                ),
                new Item("Item 2", "$29.99", Arrays.asList(new Feature("Old"), new Feature("Ugly")),
                        Arrays.asList(new Feature("Another first feature"), new Feature("Another second feature")))
        );
    }

    static class Item {
        Item(String name, String price, List<Feature> features, List<Feature> anotherFeatures) {
            this.name = name;
            this.price = price;
            this.features = features;
            this.anotherFeatures = anotherFeatures;
        }

        String name, price;
        List<Feature> features;
        List<Feature> anotherFeatures;
    }

    static class Feature {
        Feature(String description) {
            this.description = description;
        }

        String description;
    }

    private static int count = 0;
    private Map<String, Integer> counts = new Hashtable<>();

    public static void main(String[] args) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("template.mustache");
        mustache.execute(new PrintWriter(System.out), new Example()).flush();
    }

    public Function<Object, Object> count() {
        return (obj -> {
            StringTokenizer st = new StringTokenizer(obj.toString(), ":");
            String key = st.nextToken();
            count = Integer.parseInt(st.nextToken());
            counts.put(key, count);
            System.out.println(counts);
            return "";
        });
    }

    public Function<Object, Object> comma() {
        return (obj -> {
            String key = obj.toString();
            int count = 0;
            if(counts.containsKey(key)) {
                count = counts.get(obj.toString());
            }

            if(count > 1) {
                count--;
                counts.put(key, count);
                return ",";
            }

            return "";
        });
    }
/*

    public String comma() {
        if(count > 1) {
            count--;
            return ",";
        }

        count = 0;
        return "";
    }
*/

}