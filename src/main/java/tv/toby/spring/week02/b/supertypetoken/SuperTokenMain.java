package tv.toby.spring.week02.b.supertypetoken;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class SuperTokenMain {

    static class Super {

    }


    public static void main(String[] args) {

        TypeReferenceMap map = new TypeReferenceMap();

        map.put(new TypeReference<String>() {}, "abc");
//        map.put(new TypeReference<String>() {}, "def");
        map.put(new TypeReference<List<String>>() {}, Arrays.asList("A", "B", "C"));

        String s = map.get(new TypeReference<String>() {});
        List<String> ls = map.get(new TypeReference<List<String>>() {});

        System.out.println(s);
        System.out.println(ls);
    }
}
