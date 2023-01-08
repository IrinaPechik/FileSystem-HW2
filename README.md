# Консольное приложение для обнаружения зависимостей в файловой иерархии, выполненное в качестве ДЗ№2 по Java.
**Печик Ирина Юрьевна, БПИ-217**

## Задание:
Имеется корневая папка. В этой папке могут находиться текстовые файлы, а также
другие папки. В других папках также могут находиться текстовые файлы и папки
(уровень вложенности может оказаться любым).
В каждом файле может быть ни одной, одна или несколько директив формата:
require ‘<путь к другому файлу от корневого каталога>’
Директива означает, что текущий файл зависит от другого указанного файла.
Необходимо выявить все зависимости между файлами, построить сортированный
список, для которого выполняется условие: если файл А, зависит от файла В, то файл
А находится ниже файла В в списке.
Осуществить конкатенацию файлов в соответствии со списком. Если такой список
построить невозможно (существует циклическая зависимость), программа должна
вывести соответствующее сообщение.

## Подход к решению:
Для выстравивания иерархии файловой системы я буду использовать **топологическую сортировку**.

  **Топологическая сортировка** позволяет выстроить вершины бесконтурного ориентированного графа
  в соответствии с логическим порядком и представить граф ввиде уровней.

  Вершины в данном случае - это файлы в корневой папке, между которыми имеется зависимость.
  Файлы не зависящие друг от друга хранятся в другом виде.
  Связь с вершинами имеется, если между файлами найдена зависимость. 
  По условию задачи зависимость между файлами существует тогда и только тогда, когда в зависящем файле прописана строка

  **require <'путь к файлу, от которого зависит текущий'>**
  
## О реализации:
* Пользователь вводит **путь к корневой папке**, в которой и лежит вся иерархия файловой системы.
  Корневая папка может быть пуста, а может и не существовать - программа в обоих случаях выведет соответствующее сообщение.
  Строгих правил к вводу пути нет. Важно, чтобы путь был корректен на данной ОС.
  
* Происходит поиск всех файлов во всей иерархии данной корневой папки.

* Сканируется каждый файл и из него достаётся информация от зависимостях с другими файлами и текстовое содержимое.

* Строится граф файловой системы.
    Проверяется - является ли данный граф деревом (т.е. не содержит ли он циклов). 
    В противном случае - выводится соответствующее сообщение.
  
* На основе полученного дерева, в консоль выводится:
    **сортированный список зависимостей файлов** и **результат конкатенации файлов данной иерархии**.
