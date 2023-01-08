package org.example;

import java.io.File;
import java.util.*;

public class FileGraph implements Graph, PathCreator {
    private final Map<StructInformation, List<File>> dependencies;
    private final Map<StructInformation, List<StructInformation>> edges;
    private Vector<StructInformation> sortedNodes;

    private final File rootFolder;

    FileGraph(File rootFolder) {
        dependencies = new HashMap<>();
        edges = new HashMap<>();
        this.rootFolder = rootFolder;
    }

    /**
     * Добавление зависимостей между файлами.
     * @param parent Файл.
     * @param child Зависящий от данного файл.
     */
    public void addDependency(MyFileStructInformation parent, File child) {
        if (!dependencies.containsKey(parent)) {
            dependencies.put(parent, new LinkedList<>());
        }
        if (child != null) {
            dependencies.get(parent).add(child);
        }
    }

    /**
     * Добавление очередной вершины в граф.
     * @param parent Вершина-родитель.
     * @param child Вершина-ребёнок данного родителя.
     */
    public void eddEdge(StructInformation parent, File child) {
        for (var file : dependencies.keySet()) {
            if (Objects.equals(file.getFile().toString(), child.toString())) {
                if (!edges.containsKey(parent)) {
                    edges.put(parent, new LinkedList<>());
                }
                edges.get(parent).add(file);
            }
        }
    }
    /**
     * Построение графа, где каждая файловая вершина имеет обёртку.
     * Вызов топологической сортировки, если файловый граф не зациклен.
     */
    public void startSort() {
        try {
            for (var edge : dependencies.keySet()) {
                for (File file : dependencies.get(edge)) {
                    eddEdge(edge, file);
                }
            }
            // Теперь dependencies хранит файлы, которые ни от кого не зависят.
            dependencies.keySet().removeAll(edges.keySet());
            if (isTree()) {
                topologicalSort();
            } else {
                System.out.println("Файловая система зациклена.");
            }
        } catch (NullPointerException e) {
            System.out.println("Ошибка сортировки");
        }
    }

    /**
     * Рекурсивная топологическая сортировка.
     * @param node Текущая вершина файлового графа.
     * @param visitedNodes Посещённые вершины.
     * @param vec Сортированный список вершин.
     */
    private void topologicalSort(StructInformation node, Set<String> visitedNodes, Vector<StructInformation> vec) {
        // Помечаем переданную вершину как посещённую.
        visitedNodes.add(node.getPath());
        if (edges.get(node) != null) {
            for (var dependentNode : edges.get(node)) {
                // Если данная зависимая вершина ещё не посещена,
                // вызываем для неё рекурсивно эту же функцию.
                if (!visitedNodes.contains(dependentNode.getPath())) {
                    topologicalSort(dependentNode, visitedNodes, vec);
                }
            }
        }
        vec.add(node);
    }

    /**
     * Топологическая сортировка. Где вершины дерева - файлы.
     */
    public void topologicalSort() {
        sortedNodes = new Vector<>();
        Set<String> visitedNodes = new HashSet<>();

        // Проделываем топологическую сортировку для каждой вершины.
        for (var node : edges.keySet()) {
            if (!visitedNodes.contains(node.getPath())) {
                topologicalSort(node, visitedNodes, sortedNodes);
            }
        }
        print();
    }

    /**
     * Проверка того, является ли граф деревом (т.е. проверка на наличие циклов в графе).
     * @return true - граф является деревом; false - граф не дерево.
     */
    public boolean isTree() {
        boolean isTree = true;
        for (var key : edges.keySet()) {
            for (var file : edges.get(key)) {
                if (edges.containsKey(file) &&
                        edges.get(file).contains(key)) {
                    isTree = false;
                    break;
                }
            }
        }
        return isTree;
    }


    /**
     * Вывод иерархии файлов и результата конкатенации текста.
     */
    public void print() {
        if (sortedNodes == null || sortedNodes.size() == 0) {
            System.out.println("В иерархии данной файловой системы не было найдено" +
                    " зависимых файлов.");
            return;
        }
        System.out.println("\nСортированный список зависимостей файлов:\n");
        for (var sortedNode : sortedNodes) {
            System.out.println(erasePath(sortedNode.getFile(), rootFolder));
        }
        System.out.println("\nРезультат конкатенации файлов данной иерархии:\n");
        for (var sortedNode : sortedNodes) {
            System.out.println(sortedNode.getText());
        }
    }
}