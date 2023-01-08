package org.example;

import java.io.File;

public interface Graph {
    void eddEdge(StructInformation parent, File child);

    void topologicalSort();
    boolean isTree();
    void print();
}
