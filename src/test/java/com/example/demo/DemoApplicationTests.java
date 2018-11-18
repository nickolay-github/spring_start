package com.example.demo;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static guru.nidi.graphviz.model.Factory.nodeAttrs;


public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void graphTest() throws IOException {

        MutableGraph g = mutGraph("example1").setDirected(true).use((gr, ctx) -> {
            mutNode("b");
            nodeAttrs().add(Color.RED);
            mutNode("a").addLink(mutNode("b")).add("weight", 1);
            mutNode("a").addLink(mutNode("c")).add("weight", 8);
        });
        Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("example/ex1i.png"));

    }

}
