import db.GetFromDB;
import db.ModificationDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int n = 1;
        while (n != 0) {
            System.out.println("1.Подсчитать количество файлов в заданном каталоге, включая вложенные файлы и каталоги");
            System.out.println("2.Подсчитать место, занимаемое на диске содержимым заданного каталога");
            System.out.println("3.Найти в базе файлы по заданной маске с выдачей полного пути ");
            System.out.println("4.Переместить файлы и подкаталоги из одного каталога в другой ");
            System.out.println("5.Добавить файл или каталог");
            System.out.println("6.Удалить файлы и каталоги заданного каталога");
            System.out.println("7.Определить полный путь заданного файла (каталога)");
            System.out.println("0.Выход");
            n = scanner.nextInt();
            switch (n) {
                case 1: {
                    System.out.println("Введите название каталога");
                    String str = scanner.next();
                    case1(str);
                    break;
                }
                case 2: {
                    System.out.println("Введите название каталога");
                    String str = scanner.next();
                    case2(str);
                    break;
                }
                case 3: {
                    System.out.println("Введите маску");
                    String str = scanner.next();
                    case3(str);
                    break;
                }
                case 4: {
                    System.out.println("Введите название нужного каталога");
                    String name = scanner.next();
                    System.out.println("Введит в какой каталог переместить");
                    String moveTo = scanner.next();
                    System.out.println(ModificationDB.moveCatalog(name, moveTo));
                    break;
                }
                case 5: {
                    System.out.println("Выберите что добавить \n 1. Файл \n 2.Каталог");
                    if (scanner.nextInt() == 1) {
                        System.out.println("Введите название файла");
                        String name = scanner.next();
                        System.out.println("Введите размер файла");
                        Integer size = scanner.nextInt();
                        System.out.println("Введите родительский каталог");
                        String parent = scanner.next();
                        ModificationDB.addFile(name, size, parent);
                    } else {
                        System.out.println("Введите название каталога");
                        String name = scanner.next();
                        System.out.println("Введите родительский каталог или null если его нет");
                        String parent = scanner.next();
                        if (parent.equals("null")) {
                            ModificationDB.addCatalog(name);
                        } else {
                            ModificationDB.addCatalog(name, parent);
                        }
                    }

                }
                case 6: {
                    System.out.println("Введите название файла");
                    String name = scanner.next();
                    ModificationDB.deleteFile(name);
                }
                case 7: {
                    System.out.println("Введите название файла");
                    String str = scanner.next();
                    case7(str);
                    break;
                }

                default: {
                    break;
                }
            }


        }
    }

    private static void case7(String name) throws SQLException {
        ResultSet resultSet = GetFromDB.getFullPathOfFile(name);
        while (resultSet.next()) {
            System.out.println("id=" + resultSet.getInt(1) + "  " + resultSet.getString(2) + " parentId = " + resultSet.getInt(3));

        }
        System.out.println();

    }

    private static void case1(String name) throws SQLException {
        ResultSet resultSet = GetFromDB.getCountOfFiles(name);
        resultSet.next();
        System.out.println(resultSet.getInt(1) - 1);
        System.out.println();
    }

    private static void case2(String name) throws SQLException {
        ResultSet resultSet = GetFromDB.getCostOfFiles(name);
        resultSet.next();
        System.out.println(resultSet.getInt(1));
        System.out.println();
    }

    private static void case3(String name) throws SQLException {
        ResultSet resultSet = GetFromDB.getFilesFromMask(name);
        while (resultSet.next()) {
            ResultSet rs = GetFromDB.getFullPathOfFile(resultSet.getString(1));
            while (rs.next()) {
                System.out.println("id=" + rs.getInt(1) + "  " + rs.getString(2) + " parentId = " + rs.getInt(3));
            }
            System.out.println();
            System.out.println();
            System.out.println();

        }
        System.out.println();
        System.out.println();
    }
}
