package test.design;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Mutables {
    class Grade {
        private int minSalary, maxSalary;

        public Grade(int minSalary, int maxSalary) {
            this.minSalary = minSalary;
            this.maxSalary = maxSalary;
        }

        public void setMinSalary(int minSalary) {
            this.minSalary = Math.min(minSalary, maxSalary);
        }

        public void setMaxSalary(int maxSalary) {
            this.maxSalary = Math.max(minSalary, maxSalary);
        }

        public int getMaxSalary() {
            return maxSalary;
        }

        public int getMinSalary() {
            return minSalary;
        }
    }

    class Employee {
        private Grade grade;
        private int salary;

        public Employee(Grade grade, int salary) {
            setGrade(grade);
            setSalary(salary);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
            if (salary < grade.getMinSalary()) {
                salary = grade.getMinSalary();
            }
        }

        public void setSalary(int newSalary) {
            this.salary = newSalary;
            if (salary > grade.getMaxSalary()) {
                salary = grade.getMaxSalary();
            }
        }

        public Grade getGrade() {
            return grade;
        }

        public int getSalary() {
            return salary;
        }
    }

    @Test
    public void minSalary() {
        Grade junior = new Grade(40000, 80000);
        Employee emp = new Employee(junior, 30000);
        assertEquals(junior.getMinSalary(), emp.getSalary());
        emp.getGrade().setMaxSalary(5000);

    }

}
