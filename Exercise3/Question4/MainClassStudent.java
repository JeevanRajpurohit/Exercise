package Question4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainClassStudent {
    public static void main(String[] args) {
        //  4th Question
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "Jeevan", 99));
        studentList.add(new Student(2, "Rakesh", 89));
        studentList.add(new Student(3, "Bhavesh", 79));
        studentList.add(new Student(4, "Raju", 79));
        studentList.add(new Student(5, "Ramesh", 100));

//        //4th(a)
        Comparator<Student> comp = (i, j) -> i.getMarks() > j.getMarks() ? -1 : 1;
        studentList.sort(comp);
        //or
           studentList.sort(Comparator.comparing(Student::getMarks).reversed());
        System.out.println(studentList);

//
//       // 4th(b)
        studentList.sort(Comparator.comparing(Student::getName));
       // studentList.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        System.out.println(studentList);

        //using single comparator with both sorting condition
        studentList.sort(Comparator.comparing(Student::getMarks).reversed()
                          .thenComparing(Student::getName));
        System.out.println(studentList);

        //4th(c)
        List<Student> topThreeStudent = studentList.stream().sorted(Comparator.comparing(Student::getMarks).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(topThreeStudent);



    }
}
